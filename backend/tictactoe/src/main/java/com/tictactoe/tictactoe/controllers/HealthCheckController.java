package com.tictactoe.tictactoe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck() {
        return ResponseEntity.ok("Service healthy");
    }
}
