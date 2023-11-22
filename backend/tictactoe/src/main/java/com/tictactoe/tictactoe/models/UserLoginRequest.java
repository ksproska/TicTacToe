package com.tictactoe.tictactoe.models;

import javax.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
