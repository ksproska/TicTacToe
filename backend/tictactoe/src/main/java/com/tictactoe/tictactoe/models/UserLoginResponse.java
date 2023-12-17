package com.tictactoe.tictactoe.models;

public record UserLoginResponse(
        Long userId,
        String token
) {}
