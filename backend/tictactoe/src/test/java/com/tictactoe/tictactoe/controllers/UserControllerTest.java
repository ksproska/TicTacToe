package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.UserLoginResponse;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${url.without.port.number}")
    private String urlWithoutPortNumber;

    @Autowired
    UserRepository userRepository;

//    @Test
//    void signup() {
//        String username = "kamila";
//        String password = "pass";
//        long expectedId = 1L;
//
//        assertThat(
//                this.restTemplate.postForObject(
//                        urlWithoutPortNumber + port + "/signup",
//                        new UserCreateRequest(username, password),
//                        UserLoginResponse.class
//                ).userId()
//        ).isEqualTo(expectedId);
//        Optional<User> createdUser = userRepository.findById(expectedId);
//        assertThat(createdUser).isPresent();
//        var user = createdUser.get();
//        assertThat(user.getId()).isEqualTo(expectedId);
//        assertThat(user.getUsername()).isEqualTo(username);
//        assertThat(user.getPassword()).isEqualTo(password);
//    }
}
