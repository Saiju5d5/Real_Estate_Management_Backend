package com.realestate.rems.service;

import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Service
 * Contains business logic related to users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Update user by ID.
     */
    @Transactional
    public User updateUser(Long id, com.realestate.rems.dto.UserUpdateDTO userUpdateDTO) {
        User user = getUserById(id);
        
        // Update email if provided
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            // Check if email is already taken by another user
            userRepository.findByEmail(userUpdateDTO.getEmail())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(id)) {
                            throw new IllegalArgumentException("Email already exists: " + userUpdateDTO.getEmail());
                        }
                    });
            user.setEmail(userUpdateDTO.getEmail());
        }
        
        // Update password only if provided (optional)
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }
        
        // Update roles if provided
        if (userUpdateDTO.getRoles() != null && !userUpdateDTO.getRoles().isEmpty()) {
            user.setRoles(userUpdateDTO.getRoles());
        }
        
        // Update enabled status if provided
        if (userUpdateDTO.getEnabled() != null) {
            user.setEnabled(userUpdateDTO.getEnabled());
        }
        
        return userRepository.save(user);
    }

    /**
     * Check if the authenticated user is the owner of the resource.
     */
    public boolean isOwner(String email, Long userId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getId().equals(userId);
    }

    /**
     * Delete user by ID.
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
