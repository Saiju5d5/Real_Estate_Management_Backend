package com.realestate.rems.controller;

import com.realestate.rems.config.JwtUtil;
import com.realestate.rems.dto.LoginRequestDTO;
import com.realestate.rems.dto.LoginResponseDTO;
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

    // ✅ REGISTER with validation
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ FIXED & CLEAN LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        User user = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(user.getEmail());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                user.getEmail(),
                user.getRoles()
        );

        return ResponseEntity.ok(response);
    }
}
