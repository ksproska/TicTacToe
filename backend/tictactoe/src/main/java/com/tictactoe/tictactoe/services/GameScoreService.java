package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.entities.GameScore;
import com.tictactoe.tictactoe.repositories.GameScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameScoreService {
    private final GameScoreRepository gameScoreRepository;

    @Autowired
    public GameScoreService(GameScoreRepository gameScoreRepository) {
        this.gameScoreRepository = gameScoreRepository;
    }

    public void saveGameScore(GameScore gameScore) {
        gameScoreRepository.save(gameScore);
    }
}
