package com.tictactoe.tictactoe.configs;

import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Component
public class CognitoApi {
    @Value("${amazon.cognito.client-id}")
    private String clientId;

    @Value("${amazon.cognito.user-pool}")
    private String userPoolId;

    private final SecretHashCalculator secretHashCalculator;
    private final CognitoIdentityProviderClient cognitoClient;

    @Autowired
    public CognitoApi(
            SecretHashCalculator secretHashCalculator,
            CognitoIdentityProviderClient cognitoIdentityProviderClient
    ) {
        this.secretHashCalculator = secretHashCalculator;
        this.cognitoClient = cognitoIdentityProviderClient;
    }

    public String signUpToCognito(UserCreateRequest userCreateRequest) {
        String username = userCreateRequest.username();
        String secretHash = secretHashCalculator.calculateSecretHash(username, clientId);
        String password = userCreateRequest.password();
        cognitoClient.signUp(
                SignUpRequest
                        .builder()
                        .clientId(clientId)
                        .secretHash(secretHash)
                        .username(username)
                        .password(password)
                        .build()
        );
        cognitoClient.adminSetUserPassword(
                AdminSetUserPasswordRequest
                        .builder()
                        .userPoolId(userPoolId)
                        .username(username)
                        .password(password)
                        .permanent(true)
                        .build()
        );
        return initiateAuth(username, password, secretHash);
    }

    public String logInInCognito(UserLoginRequest request) {
        String username = request.username();
        String password = request.password();
        String secretHash = secretHashCalculator.calculateSecretHash(username, clientId);
        return initiateAuth(username, password, secretHash);
    }

    private String initiateAuth(String username, String password, String secretHash) {
        return cognitoClient.initiateAuth(
                        InitiateAuthRequest.builder()
                                .clientId(clientId)
                                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                                .authParameters(Map.of(
                                        "USERNAME", username,
                                        "PASSWORD", password,
                                        "SECRET_HASH", secretHash
                                ))
                                .build()
                ).authenticationResult()
                .accessToken();
    }

    public String getUsernameForToken(String token) {
        return cognitoClient
                .getUser(
                        GetUserRequest
                                .builder()
                                .accessToken(token)
                                .build()
                ).username();
    }
}
