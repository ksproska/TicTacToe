package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.MoveRequest;
import com.tictactoe.tictactoe.services.GameScoreService;
import com.tictactoe.tictactoe.services.GameService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final GameService gameService;
    private final GameScoreService gameScoreService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketController(GameService gameService, GameScoreService gameScoreService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.gameScoreService = gameScoreService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/move/{gameId}")
    @Transactional
    public void move(@DestinationVariable Long gameId, MoveRequest moveRequest) {
        var game = this.gameService.getGameById(gameId);
        var validatedMoveResponse = game.move(moveRequest);
        this.gameService.updateGame(game);
        validatedMoveResponse
                .getGameFinalScore(game)
                .ifPresent(gameScoreService::saveGameScore);
        this.simpMessagingTemplate.convertAndSend("/topic/move/" + gameId, validatedMoveResponse);
    }
}
