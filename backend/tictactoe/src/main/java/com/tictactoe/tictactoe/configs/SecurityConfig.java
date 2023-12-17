package com.tictactoe.tictactoe.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Component
public class SecurityConfig {
    @Value("${amazon.cognito.access-key}")
    private String accessKey;

    @Value("${amazon.cognito.secret-key}")
    private String secretKey;

    @Value("${amazon.cognito.session-token}")
    private String sessionToken;

    @Bean
    public CognitoIdentityProviderClient getCognitoIdentityProviderClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsSessionCredentials.create(
                                        accessKey,
                                        secretKey,
                                        sessionToken
                                )
                        )
                ).build();
    }
}
