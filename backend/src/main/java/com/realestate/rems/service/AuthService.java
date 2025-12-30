package com.realestate.rems.service;

import com.realestate.rems.exception.InvalidCredentialsException;
import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        // ✅ Default role as STRING
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("CUSTOMER"));
        }

        return userRepository.save(user);
    }

    public User login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return user;
    }
}
