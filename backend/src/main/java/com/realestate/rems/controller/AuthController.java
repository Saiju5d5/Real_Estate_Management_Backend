package com.realestate.rems.controller;

import com.realestate.rems.config.JwtUtil;
import com.realestate.rems.dto.LoginRequestDTO;
import com.realestate.rems.dto.LoginResponseDTO;
import com.realestate.rems.dto.UserRegistrationDTO;
import com.realestate.rems.model.User;
import com.realestate.rems.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

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
                user.getRoles(),
                user.getId());

        return ResponseEntity.ok(response);
    }
}
