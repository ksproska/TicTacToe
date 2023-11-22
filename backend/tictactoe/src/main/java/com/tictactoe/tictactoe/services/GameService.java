package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.entities.Game;
import com.tictactoe.tictactoe.models.entities.GameSign;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.repositories.GameRepository;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<User> user = userRepository.findById(playerId);
        if (user.isEmpty()) {
            throw new IllegalStateException("Could not find user with id " + playerId);
        }
        gameRepository.findAllByPlayer1_IdOrPlayer2_IdAndWinnerNull(playerId, playerId).forEach(
                game -> {
                    game.setWinner(GameSign.NONE);
                    gameRepository.save(game);
                }
        );
        Optional<Game> gameOptional = gameRepository.findFirstByPlayer2IsNullAndPlayer1_IdNotAndWinnerNull(playerId);
        if (gameOptional.isPresent()) {
            var game = gameOptional.get();
            game.setPlayer2(user.get());
            if (Optional.ofNullable(game.getPlayerTurn()).isEmpty()) {
                game.setPlayerTurn(user.get());
            }
            gameRepository.save(game);
            return game;
        }
        var newGame = new Game(user.get());
        return gameRepository.save(newGame);
    }
}
