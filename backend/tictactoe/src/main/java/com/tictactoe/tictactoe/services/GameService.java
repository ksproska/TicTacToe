package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.entities.Game;
import com.tictactoe.tictactoe.models.entities.GameSign;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.repositories.GameRepository;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game getGameById(Long gameId) {
        return gameRepository.getReferenceById(gameId);
    }

    public void updateGame(Game game) {
        this.gameRepository.save(game);
    }

    public Game getGameByPlayerId(Long playerId) {
        User user = userRepository
                .findById(playerId)
                .orElseThrow(() -> new IllegalStateException("Could not find user with id " + playerId));
        gameRepository
                .findAllByPlayer1_IdOrPlayer2_IdAndWinnerNull(playerId, playerId)
                .forEach(
                        game -> {
                            game.setWinner(GameSign.NONE);
                            gameRepository.save(game);
                        }
                );
        var gameToReturn = gameRepository
                .findFirstByPlayer2IsNullAndPlayer1_IdNotAndWinnerNull(playerId)
                .map(
                        game -> {
                            game.setPlayer2(user);
                            return game;
                        }
                ).orElse(new Game(user));
        return gameRepository.save(gameToReturn);
    }
}
