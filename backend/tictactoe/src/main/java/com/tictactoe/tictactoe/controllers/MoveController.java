package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.MoveRequest;
import com.tictactoe.tictactoe.models.ValidatedMoveResponse;
import com.tictactoe.tictactoe.services.GameScoreService;
import com.tictactoe.tictactoe.services.GameService;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.*;

@Controller
public class MoveController {
    private final GameService gameService;
    private final GameScoreService gameScoreService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Map<String, String> sessions;

    public MoveController(GameService gameService, GameScoreService gameScoreService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.gameScoreService = gameScoreService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sessions = new HashMap<>();
    }

    @MessageMapping("/requested-move/{gameId}")
    @Transactional
    public void move(@DestinationVariable Long gameId, MoveRequest moveRequest) {
        var game = this.gameService.getGameById(gameId);
        var validatedMoveResponse = game.move(moveRequest);
        this.gameService.updateGame(game);
        validatedMoveResponse
                .getGameFinalScore(game)
                .ifPresent(gameScoreService::saveGameScore);
        this.simpMessagingTemplate.convertAndSend("/topic/verified-move/" + gameId, validatedMoveResponse);
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        var headers = getNativeHeaders(event.getMessage());
        var id = getId(headers, "id");
        var destination = getId(headers, "destination");
        sessions.put(id, destination);
    }

    @EventListener
    @Transactional
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {
        var headers = getNativeHeaders(event.getMessage());
        var id = getId(headers, "id");
        var destination = sessions.get(id);
        var gameId = Arrays.stream(destination.split("/"))
                .reduce((first, second) -> second)
                .map(Long::parseLong)
                .orElseThrow();
        this.gameService.setGameFinishedWithoutWinner(gameId);
        this.simpMessagingTemplate.convertAndSend(destination, ValidatedMoveResponse.GAME_ABANDONED_RESPONSE);
    }

    private static <T, U> String getId(Map<T, U> headers, String id) {
        return Optional.ofNullable(headers.get(id))
                .map(List.class::cast)
                .map(list -> list.get(0))
                .map(String.class::cast)
                .orElseThrow();
    }

    private static Map getNativeHeaders(Message<byte[]> event) {
        return Optional.ofNullable(event.getHeaders().get("nativeHeaders"))
                .map(Map.class::cast)
                .orElseThrow();
    }
}
