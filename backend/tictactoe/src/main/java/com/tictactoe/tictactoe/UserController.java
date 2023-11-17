package com.tictactoe.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public List<String> listActiveUserNicks() {
        return List.of("kamila123", "szymon345", "agnieszka567", "darek890");
    }
}
