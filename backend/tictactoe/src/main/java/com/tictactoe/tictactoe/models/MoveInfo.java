package com.tictactoe.tictactoe.models;

public record MoveInfo(int index, GameSlot sign, Long nextPlayer, boolean isGameFinished) {}
