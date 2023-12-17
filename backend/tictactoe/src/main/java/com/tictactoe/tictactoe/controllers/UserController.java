package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.UserLoginRequest;
import com.tictactoe.tictactoe.models.UserLoginResponse;
import com.tictactoe.tictactoe.models.UserCreateRequest;
import com.tictactoe.tictactoe.models.UserVerificationRequest;
import com.tictactoe.tictactoe.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "${app.api.settings.cross-origin.urls}")
@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            var createdUser = userService.signUp(userCreateRequest);
            var okMessage = "User with username '" + userCreateRequest.username() + "' created.";
            LOG.info(okMessage);
            return ResponseEntity.ok(new UserLoginResponse(createdUser.getId()));
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponse> authenticate(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestBody UserVerificationRequest userVerificationRequest) {
        return ResponseEntity.ok(userService.verify(userVerificationRequest));
    }

    @GetMapping("/list-active-user-nicks")
    @ResponseBody
    public List<String> listActiveUserNicks() {
        return List.of(
                "this endpoint is not yet implemented",
                "but backend works"
        );
    }
}
