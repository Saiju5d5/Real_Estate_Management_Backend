package com.realestate.rems.service;

import com.realestate.rems.dto.UserRegistrationDTO;
import com.realestate.rems.exception.InvalidCredentialsException;
import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRegistrationDTO registrationDTO) {
        // Check if email already exists
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + registrationDTO.getEmail());
        }

        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setEnabled(true);

        // Set default role if not provided
        if (registrationDTO.getRoles() == null || registrationDTO.getRoles().isEmpty()) {
            user.setRoles(Set.of("CUSTOMER"));
        } else {
            user.setRoles(registrationDTO.getRoles());
        }

        return userRepository.save(user);
    }

    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Account is disabled");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return user;
    }
}
