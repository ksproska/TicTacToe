package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.AuthRequest;
import com.tictactoe.tictactoe.models.AuthResponse;
import com.tictactoe.tictactoe.models.User;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        var userDetails = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("No user of name '" + request.username() + "' found."));
        if(!userDetails.getPassword().equals(request.password())) {
            throw new IllegalStateException("Incorrect password.");
        }
        return new AuthResponse(userDetails.getId());
    }
}
