package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.api.AuthenticationApi;
import com.tictactoe.tictactoe.models.GameRequest;
import com.tictactoe.tictactoe.models.entities.Game;
import com.tictactoe.tictactoe.models.entities.GameSign;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.repositories.GameRepository;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final AuthenticationApi authenticationApi;

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository, AuthenticationApi authenticationApi) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.authenticationApi = authenticationApi;
    }

    public Game getGameById(Long gameId) {
        return gameRepository.getReferenceById(gameId);
    }

    public void updateGame(Game game) {
        this.gameRepository.save(game);
    }

    public Game getGameByPlayerId(GameRequest gameRequest) {
        Long playerId = gameRequest.userId();
        String username = authenticationApi.getUsernameForToken(gameRequest.token());
        User user = userRepository
                .findById(playerId)
                .orElseThrow(() -> new IllegalStateException("Could not find user with id " + playerId));
        if (!Objects.equals(username, user.getUsername())) {
            throw new IllegalStateException("Request unauthorised!");
        }
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
