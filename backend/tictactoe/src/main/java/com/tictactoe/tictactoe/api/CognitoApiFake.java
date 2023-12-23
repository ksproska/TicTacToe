package com.tictactoe.tictactoe.api;

import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserLoginRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class CognitoApiFake implements AuthenticationApi {
    @Override
    public String signUpToCognito(UserCreateRequest userCreateRequest) {
        return userCreateRequest.username();
    }

    @Override
    public String logInInCognito(UserLoginRequest request) {
        return request.username();
    }

    @Override
    public String getUsernameForToken(String token) {
        return token;
    }
}
