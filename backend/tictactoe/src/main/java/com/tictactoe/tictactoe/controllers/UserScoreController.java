package com.tictactoe.tictactoe.controllers;

import com.tictactoe.tictactoe.models.UserScoreResponse;
import com.tictactoe.tictactoe.services.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "${app.api.settings.cross-origin.urls}")
@RestController
public class UserScoreController {
    private final UserScoreService userScoreService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @GetMapping("/list-users-scores")
    @ResponseBody
    public List<UserScoreResponse> listUsersScores() {
        return userScoreService.listUsersScores();
    }
}
