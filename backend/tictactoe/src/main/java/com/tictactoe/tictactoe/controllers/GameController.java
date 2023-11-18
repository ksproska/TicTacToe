package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.Game;
import com.tictactoe.tictactoe.services.GameService;
import com.tictactoe.tictactoe.models.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/list-games")
    @ResponseBody
    public List<Game> listGames() {
        return gameService.getAllGames();
    }

    @PutMapping("/create-game")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        var createdGame = gameService.saveGame(game);
        return ResponseEntity.ok(createdGame);
    }

    @GetMapping("/list-users-scores")
    @ResponseBody
    public List<UserScore> listUsersScores() {
        return gameService.listUsersScores();
    }
}
