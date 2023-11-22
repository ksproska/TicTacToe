package com.tictactoe.tictactoe.models;

import com.tictactoe.tictactoe.models.entities.GameSign;
import com.tictactoe.tictactoe.models.entities.User;

import java.util.List;

import static com.tictactoe.tictactoe.models.entities.GameSign.NONE;

public record MoveRequest(
        Long playerId,
        int index
) {
    public void validateForGame(User playerTurn, List<GameSign> gameSlots) {
        if (!playerTurn.getId().equals(playerId)) {
            throw new IllegalStateException("it is not this player turn");
        }
        if (index >= gameSlots.size() || index < 0) {
            throw new IllegalStateException("incorrect index " + index);
        }
        if (gameSlots.get(index) != NONE) {
            throw new IllegalStateException("index already filled");
        }
    }
}
