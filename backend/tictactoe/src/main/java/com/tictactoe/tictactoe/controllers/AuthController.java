package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.AuthRequest;
import com.tictactoe.tictactoe.models.AuthResponse;
import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.VerificationRequest;
import com.tictactoe.tictactoe.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            var createdUser = userService.saveUser(userCreateRequest);
            var okMessage = "User with username '" + userCreateRequest.username() + "' created.";
            LOG.info(okMessage);
            return ResponseEntity.ok(new AuthResponse(createdUser.getId()));
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestBody VerificationRequest verificationRequest) {
        return ResponseEntity.ok(userService.verify(verificationRequest));
    }
}
