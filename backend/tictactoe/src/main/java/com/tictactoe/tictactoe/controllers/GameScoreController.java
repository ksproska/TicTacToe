package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.GameScore;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.services.GameScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class GameScoreController {
    private final GameScoreService gameScoreService;

    @Autowired
    public GameScoreController(GameScoreService gameScoreService) {
        this.gameScoreService = gameScoreService;
    }

    @GetMapping("/list-game-scores")
    @ResponseBody
    public List<GameScore> listGameScores() {
        return gameScoreService.getAllGameScores();
    }

    @PutMapping("/create-game-score")
    public ResponseEntity<String> createGameScore(@RequestBody GameScore gameScore) {
        var createdGame = gameScoreService.saveGameScore(gameScore);
        var winner =
                Optional.ofNullable(createdGame.getWinner())
                        .map(User::getId)
                        .map(Object::toString)
                        .orElse("");
        return ResponseEntity.ok().body(
                "Created game for '" + createdGame.getPlayer1().getId() + "' vs '" +
                        createdGame.getPlayer2().getId() +
                "' : winner :'" + winner + "'");
    }
}
