package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.GameInfo;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PutMapping("/get-game")
    public ResponseEntity<GameInfo> getGame(@RequestBody User player) {
        var game = gameService.getGame(player);
        return ResponseEntity.ok(game.getGameInfo(player.getId()));
    }
}
