package com.tictactoe.tictactoe.models;

import java.util.List;

public record GameInfo(Long gameId, List<GameSlot> gameSlots, boolean startsFirst, GameSlot sign) {}
