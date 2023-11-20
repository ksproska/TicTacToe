package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class UserController {
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
}
