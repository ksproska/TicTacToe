package com.tictactoe.tictactoe;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @GetMapping("/list-users")
    @ResponseBody
    public List<User> listUsers() {
        return List.of(new User("Kamila", "pass"));
    }
    @GetMapping("/list-active-user-nicks")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public List<String> listActiveUserNicks() {
        return List.of("kamila123", "szymon345", "agnieszka567", "darek890");
    }
}
