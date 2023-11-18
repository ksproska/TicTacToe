package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.Game;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.models.UserScore;
import com.tictactoe.tictactoe.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserScoreService {
    private final GameRepository gameRepository;

    @Autowired
    public UserScoreService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<UserScore> listUsersScores() {
        return gameRepository
                .findAll()
                .stream()
                .map(Game::getWinner)
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
                .map(x -> new UserScore(x.getKey(), x.getValue()))
                .sorted(Comparator.comparing(UserScore::score).reversed())
                .toList();
    }
}
