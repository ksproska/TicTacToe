package com.tictactoe.tictactoe;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list-users")
    @ResponseBody
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/list-active-user-nicks")
    @ResponseBody
    public List<String> listActiveUserNicks() {
        return List.of("kamila123", "szymon345", "agnieszka567", "darek890");
    }

    @PutMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            var createdUser = userService.saveUser(user);
            var okMessage = "User with username '" + createdUser.getUsername() + "' created.";
            LOG.info(okMessage);
            return ResponseEntity.ok().body(okMessage);
        } catch (DataIntegrityViolationException e) {
            var cause = Optional.ofNullable(e.getRootCause()).orElse(e.getCause()).getMessage();
            if (cause.startsWith("Duplicate entry '" + user.getUsername() + "' for key ")) {
                var errorMessage = "UNABLE TO CREATE USER: username '" + user.getUsername() + "' already exists.";
                LOG.info(errorMessage);
                return ResponseEntity.badRequest().body(errorMessage);
            }
            LOG.error(cause);
            return ResponseEntity.badRequest().build();
        }
    }
}
