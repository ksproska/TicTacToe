package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.Game;
import com.tictactoe.tictactoe.models.User;
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

    public Game getGame(Long playerId) {
        Optional<User> user = userRepository.findById(playerId);
        if(user.isEmpty()) {
            throw new IllegalStateException("Could not find user with id " + playerId);
        }
        Optional<Game> gameOptional = gameRepository.findFirstByPlayer2IsNullAndPlayer1_IdNot(playerId);
        if (gameOptional.isPresent()) {
            var game = gameOptional.get();
            game.setPlayer2(user.get());
            gameRepository.flush();
            return game;
        }
        var newGame = new Game(user.get());
        return gameRepository.save(newGame);
    }

    public Game getGameById(Long gameId) {
        return gameRepository.getReferenceById(gameId);
    }

    public void update(Game game) {
        this.gameRepository.save(game);
    }
}
