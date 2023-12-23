package com.tictactoe.tictactoe.configs;

import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserLoginRequest;

public interface AuthenticationApi {
    String signUpToCognito(UserCreateRequest userCreateRequest);

    String logInInCognito(UserLoginRequest request);

    String getUsernameForToken(String token);
}
