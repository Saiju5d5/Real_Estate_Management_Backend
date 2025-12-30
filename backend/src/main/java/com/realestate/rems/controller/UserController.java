package com.realestate.rems.controller;

import com.realestate.rems.dto.UserUpdateDTO;
import com.realestate.rems.model.ApiResponse;
import com.realestate.rems.model.User;
import com.realestate.rems.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Management Controller
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all users (ADMIN only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        // Don't return passwords
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID (ADMIN only for now - can be enhanced for self-access)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        // Don't return password
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    /**
     * Update user (ADMIN only)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedUser = userService.updateUser(id, userUpdateDTO);
        // Don't return password
        updatedUser.setPassword(null);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user (ADMIN only)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse response = new ApiResponse(true, "User deleted successfully");
        return ResponseEntity.ok(response);
    }
}
