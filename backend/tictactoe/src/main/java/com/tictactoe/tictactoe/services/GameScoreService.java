package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.GameScore;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.repositories.GameScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameScoreService {
    private final GameScoreRepository gameScoreRepository;

    @Autowired
    public GameScoreService(GameScoreRepository gameScoreRepository) {
        this.gameScoreRepository = gameScoreRepository;
    }

    public GameScore saveGameScore(GameScore gameScore) {
        Optional<User> winner = Optional.ofNullable(gameScore.getWinner());
        if (winner.isPresent()) {
            var playersIds = List.of(gameScore.getPlayer1().getId(), gameScore.getPlayer2().getId());
            if (!playersIds.contains(winner.get().getId())) {
                throw new IllegalStateException("Winner id " + winner.get().getId() + " is not in " + playersIds);
            }
        }
        return gameScoreRepository.save(gameScore);
    }

    public List<GameScore> getAllGameScores() {
        return gameScoreRepository.findAll();
    }
}
