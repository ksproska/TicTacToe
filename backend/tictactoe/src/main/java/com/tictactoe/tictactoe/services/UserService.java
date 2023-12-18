package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.configs.CognitoApi;
import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserLoginRequest;
import com.tictactoe.tictactoe.models.UserLoginResponse;
import com.tictactoe.tictactoe.models.UserVerificationRequest;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CognitoApi cognitoApi;

    @Autowired
    public UserService(UserRepository userRepository, CognitoApi cognitoApi) {
        this.userRepository = userRepository;
        this.cognitoApi = cognitoApi;
    }

    public UserLoginResponse signUp(UserCreateRequest userCreateRequest) {
        var accessToken = cognitoApi.signUpToCognito(userCreateRequest);
        userRepository.findByUsername(userCreateRequest.username()).ifPresent(
                user -> {
                    throw new IllegalArgumentException("User with username " + userCreateRequest.username() + " already exists");
                }
        );
        var user = userRepository.save(new User(userCreateRequest.username()));
        return new UserLoginResponse(user.getId(), accessToken);
    }

    public UserLoginResponse authenticate(UserLoginRequest request) {
        var accessToken = cognitoApi.logInInCognito(request);
        User userDetails = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("No user of name '" + request.username() + "' found in database."));
        return new UserLoginResponse(userDetails.getId(), accessToken);
    }

    public boolean verify(UserVerificationRequest userVerificationRequest) {
        String accessToken = userVerificationRequest.token();
        String username;
        try {
            username = cognitoApi.getUsernameForToken(accessToken);
        } catch (NotAuthorizedException e) {
            return false;
        }
        if (!username.equals(userVerificationRequest.username())) return false;
        return userRepository
                .findByUsername(userVerificationRequest.username())
                .map(User::getId)
                .map(userId -> userId.equals(userVerificationRequest.userId()))
                .orElse(false);
    }
}
