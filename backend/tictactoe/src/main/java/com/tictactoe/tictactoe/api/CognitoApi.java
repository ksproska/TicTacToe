package com.tictactoe.tictactoe.api;

import com.tictactoe.tictactoe.configs.SecretHashCalculator;
import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Profile("!dev")
@Component
public class CognitoApi implements AuthenticationApi {
    private static final Logger LOG = LoggerFactory.getLogger(CognitoApi.class);
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

    @Override
    public String signUpToCognito(UserCreateRequest userCreateRequest) {
        String username = userCreateRequest.username();
        String secretHash = secretHashCalculator.calculateSecretHash(username, clientId);
        String password = userCreateRequest.password();
        try {
            cognitoClient.adminCreateUser(
                    AdminCreateUserRequest
                            .builder()
                            .userPoolId(userPoolId)
                            .username(username)
                            .temporaryPassword(password)
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
        } catch (CognitoIdentityProviderException e) {
            LOG.error(e.getMessage());
            throw new IllegalStateException("Issues with service, try again later.");
        }
        return initiateAuth(username, password, secretHash);
    }

    @Override
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

    @Override
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
