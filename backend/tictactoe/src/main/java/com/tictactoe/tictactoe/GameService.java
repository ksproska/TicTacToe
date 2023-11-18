package com.tictactoe.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<UserScore> listUsersScores() {
        return getAllGames()
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
                .toList();
    }
}
