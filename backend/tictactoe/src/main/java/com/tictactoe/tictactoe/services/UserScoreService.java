package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.UserScoreResponse;
import com.tictactoe.tictactoe.models.entities.GameScore;
import com.tictactoe.tictactoe.repositories.GameScoreRepository;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class UserScoreService {
    private final GameScoreRepository gameScoreRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserScoreService(GameScoreRepository gameScoreRepository, UserRepository userRepository) {
        this.gameScoreRepository = gameScoreRepository;
        this.userRepository = userRepository;
    }

    public List<UserScoreResponse> listUsersScores() {
        var allWinners = gameScoreRepository
                .findAll()
                .stream()
                .map(GameScore::getWinner)
                .filter(Objects::nonNull)
                .toList();
        return userRepository
                .findAll()
                .stream()
                .map(
                        user -> new UserScoreResponse(
                                user.getUsername(),
                                (int) allWinners.stream().filter(u -> u.equals(user)).count()
                        )
                )
                .sorted(Comparator.comparing(UserScoreResponse::score).reversed())
                .toList();
    }
}
