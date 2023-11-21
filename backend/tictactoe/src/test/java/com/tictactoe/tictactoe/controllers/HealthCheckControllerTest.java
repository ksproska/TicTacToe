package com.tictactoe.tictactoe.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${url.without.port.number}")
    private String urlWithoutPortNumber;

    @Test
    void healthcheck() {
        assertThat(this.restTemplate.getForObject(urlWithoutPortNumber + port + "/healthcheck", String.class))
                .isEqualTo("Service healthy");
    }
}