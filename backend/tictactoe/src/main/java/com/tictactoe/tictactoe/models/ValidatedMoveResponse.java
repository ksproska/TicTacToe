package com.tictactoe.tictactoe.models;

import com.tictactoe.tictactoe.models.entities.Game;
import com.tictactoe.tictactoe.models.entities.GameScore;
import com.tictactoe.tictactoe.models.entities.GameSlot;

import java.util.Optional;

public record ValidatedMoveResponse(
        int index,
        GameSlot sign,
        Long nextPlayer,
        boolean isGameFinished
) {
    public Optional<GameScore> getGameFinalScore(Game game) {
        if (!isGameFinished) {
            return Optional.empty();
        }
        return game
                .getWinnerPlayer()
                .map(winner -> new GameScore(game.getPlayer1(), game.getPlayer2(), winner));
    }
}
