package com.tictactoe.tictactoe.models;

import com.tictactoe.tictactoe.models.entities.GameSlot;

import java.util.List;

public record GameInfo(Long gameId, List<GameSlot> gameSlots, boolean enableMove, GameSlot sign) {}
