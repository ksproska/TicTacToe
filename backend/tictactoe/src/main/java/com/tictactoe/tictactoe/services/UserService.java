package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.*;
import com.tictactoe.tictactoe.models.entities.User;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(UserCreateRequest userCreateRequest) {
        userRepository.findByUsername(userCreateRequest.username()).ifPresent(
                user -> {
                    throw new IllegalArgumentException("User with username " + userCreateRequest.username() + " already exists");
                }
        );
        User newUser = new User(userCreateRequest.username(), userCreateRequest.password());
        return userRepository.save(newUser);
    }

    public UserLoginResponse authenticate(UserLoginRequest request) {
        User userDetails = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("No user of name '" + request.username() + "' found."));
        if (!userDetails.getPassword().equals(request.password())) {
            throw new IllegalStateException("Incorrect password.");
        }
        return new UserLoginResponse(userDetails.getId());
    }

    public boolean verify(UserVerificationRequest userVerificationRequest) {
        return userRepository
                .findByUsername(userVerificationRequest.username())
                .map(User::getId)
                .map(userId -> userId.equals(userVerificationRequest.userId()))
                .orElse(false);
    }
}
