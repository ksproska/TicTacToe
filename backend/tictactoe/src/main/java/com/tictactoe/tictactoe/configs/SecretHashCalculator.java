package com.tictactoe.tictactoe.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SecretHashCalculator {
    @Value("${amazon.cognito.secret}")
    private String secret;

    public String calculateSecretHash(String username, String clientId) {
        String data = username + clientId;
        byte[] hmacData = null;

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(key);
            hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(hmacData);
    }
}
