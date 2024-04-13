package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.domain.User;
import com.backgom.backgomwineback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User findById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unexpected user")
                );
    }

    public User registerNewUserAccount(String email, String rawPassword, List<String> filePath) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists.");
        }
        User newUser = new User(email, passwordEncoder.encode(rawPassword));
        return userRepository.save(newUser);
    }
}
