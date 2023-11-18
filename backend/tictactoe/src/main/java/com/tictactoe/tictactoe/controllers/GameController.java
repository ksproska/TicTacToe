package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.Game;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        var createdGame = gameService.saveGame(game);
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
