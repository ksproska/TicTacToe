package com.tictactoe.tictactoe.services;

import com.tictactoe.tictactoe.models.*;
import com.tictactoe.tictactoe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserCreateRequest userCreateRequest) {
        var userWithSameUsername = userRepository.findByUsername(userCreateRequest.username());
        if (userWithSameUsername.isPresent()) {
            throw new IllegalArgumentException("User with username " + userCreateRequest.username() + " already exists");
        }
        return this.userRepository.save(new User(userCreateRequest.username(), userCreateRequest.password()));
    }

    public UserLoginResponse authenticate(UserLoginRequest request) {
        var userDetails = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("No user of name '" + request.username() + "' found."));
        if(!userDetails.getPassword().equals(request.password())) {
            throw new IllegalStateException("Incorrect password.");
        }
        return new UserLoginResponse(userDetails.getId());
    }

    public boolean verify(UserVerificationRequest userVerificationRequest) {
        Optional<User> user = userRepository.findByUsername(userVerificationRequest.username());
        return user.isPresent() && user.get().getId().equals(userVerificationRequest.userId());
    }
}
