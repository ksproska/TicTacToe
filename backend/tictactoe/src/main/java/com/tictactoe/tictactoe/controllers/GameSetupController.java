package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.GameStartSetup;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${app.api.settings.cross-origin.urls}")
public class GameSetupController {
    private final GameService gameService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameSetupController(GameService gameService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PutMapping("/get-game")
    public ResponseEntity<GameStartSetup> getGame(@RequestBody User player) {
        var game = gameService.getGameByPlayerId(player.getId());
        game.getFirstMoveForPlayer2LateAssigment()
                .ifPresent(
                        move -> this.simpMessagingTemplate.convertAndSend(
                                "/topic/verified-move/" + game.getId(),
                                move
                        )
                );
        return ResponseEntity.ok(game.getGameStartSetup(player.getId()));
    }
}
