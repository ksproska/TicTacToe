package com.tictactoe.tictactoe.models;

import javax.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank String username, @NotBlank String password) {}
