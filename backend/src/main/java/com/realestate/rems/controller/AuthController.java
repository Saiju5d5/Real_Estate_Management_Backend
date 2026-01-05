package com.realestate.rems.controller;

import com.realestate.rems.config.JwtUtil;
import com.realestate.rems.dto.LoginRequestDTO;
import com.realestate.rems.dto.LoginResponseDTO;
import com.realestate.rems.dto.UserRegistrationDTO;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.UserRepository;
import com.realestate.rems.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        User savedUser = authService.register(registrationDTO);
        // Don't return password
        savedUser.setPassword(null);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Login and get JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        User user = authService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtUtil.generateToken(user.getEmail());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                user.getEmail(),
                user.getRole(),
                user.getId());

        return ResponseEntity.ok(response);
    }

    /**
     * Get current user profile (protected)
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new com.realestate.rems.exception.ResourceNotFoundException("User not found"));
        user.setPassword(null); // Don't return password
        return ResponseEntity.ok(user);
    }
}
