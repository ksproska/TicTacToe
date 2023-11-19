package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.UserScore;
import com.tictactoe.tictactoe.services.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200,http://0.0.0.0:4200")
@RestController
public class UserScoreController {
    private final UserScoreService userScoreService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @GetMapping("/list-users-scores")
    @ResponseBody
    public List<UserScore> listUsersScores() {
        return userScoreService.listUsersScores();
    }
}