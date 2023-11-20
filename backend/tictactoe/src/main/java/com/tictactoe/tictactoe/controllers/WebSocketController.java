package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.MoveRequest;
import com.tictactoe.tictactoe.services.GameService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketController(GameService gameService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/move/{gameId}")
    @Transactional
    public void move(@DestinationVariable Long gameId, MoveRequest moveRequest) {
        var game = this.gameService.getGameById(gameId);
        var moveInfo = game.move(moveRequest);
        this.gameService.update(game);
        this.simpMessagingTemplate.convertAndSend("/topic/move/" + gameId, moveInfo);
    }
}
