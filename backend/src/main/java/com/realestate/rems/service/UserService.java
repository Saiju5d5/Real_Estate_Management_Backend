package com.realestate.rems.service;

import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service
 * Contains business logic related to users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
     * Delete user by ID.
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
