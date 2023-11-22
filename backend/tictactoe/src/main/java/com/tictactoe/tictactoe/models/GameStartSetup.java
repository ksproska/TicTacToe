package com.tictactoe.tictactoe.models;

import com.tictactoe.tictactoe.models.entities.GameSign;

import java.util.List;

public record GameStartSetup(
        Long gameId,
        List<GameSign> gameSlots,
        boolean enableMove,
        GameSign sign
) {}
