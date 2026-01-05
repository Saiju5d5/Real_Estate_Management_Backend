package com.realestate.rems.controller;

import com.realestate.rems.dto.UserUpdateDTO;
import com.realestate.rems.model.User;
import com.realestate.rems.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    @Autowired
    private UserService userService;

    /**
     * Get own profile
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        user.setPassword(null); // Don't return password
        return ResponseEntity.ok(user);
    }

    /**
     * Update own profile
     */
    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateProfile(
            @Valid @RequestBody UserUpdateDTO userUpdateDTO,
            Authentication authentication) {
        String email = authentication.getName();
        User updatedUser = userService.updateProfile(email, userUpdateDTO);
        updatedUser.setPassword(null); // Don't return password
        return ResponseEntity.ok(updatedUser);
    }
}

