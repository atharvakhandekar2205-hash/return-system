package com.example.returnsystem.service;

import com.example.returnsystem.model.User;
import com.example.returnsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return "EMAIL_EXISTS";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        userRepository.save(user);
        return "SUCCESS";
    }
}