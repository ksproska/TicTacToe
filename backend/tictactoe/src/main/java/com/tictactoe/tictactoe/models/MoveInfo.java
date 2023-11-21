package com.tictactoe.tictactoe.models;

import com.tictactoe.tictactoe.models.entities.GameSlot;

public record MoveInfo(int index, GameSlot sign, Long nextPlayer, boolean isGameFinished) {}
