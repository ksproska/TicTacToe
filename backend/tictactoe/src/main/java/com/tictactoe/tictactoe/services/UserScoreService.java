package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.GameScore;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.models.UserScoreResponse;
import com.tictactoe.tictactoe.repositories.GameScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserScoreService {
    private final GameScoreRepository gameScoreRepository;

    @Autowired
    public UserScoreService(GameScoreRepository gameScoreRepository) {
        this.gameScoreRepository = gameScoreRepository;
    }

    public List<UserScoreResponse> listUsersScores() {
        return gameScoreRepository
                .findAll()
                .stream()
                .map(GameScore::getWinner)
                .filter(Objects::nonNull)
                .map(User::getUsername)
                .reduce(new HashMap<String, Integer>(), (hashMap, e) -> {
                            hashMap.merge(e, 1, Integer::sum);
                            return hashMap;
                        },
                        (m, m2) -> {
                            m.putAll(m2);
                            return m;
                        }
                )
                .entrySet()
                .stream()
                .map(x -> new UserScoreResponse(x.getKey(), x.getValue()))
                .sorted(Comparator.comparing(UserScoreResponse::score).reversed())
                .toList();
    }
}
