package com.realestate.rems.service;

import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.Favorite;
import com.realestate.rems.model.Property;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.FavoriteRepository;
import com.realestate.rems.repository.PropertyRepository;
import com.realestate.rems.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Favorite addFavorite(Long propertyId, Long clientId) {
        // Check if already favorited
        if (favoriteRepository.existsByPropertyIdAndClientId(propertyId, clientId)) {
            throw new IllegalArgumentException("Property is already in favorites");
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + clientId));

        if (!"client".equals(client.getRole())) {
            throw new IllegalArgumentException("Only clients can add favorites");
        }

        Favorite favorite = Favorite.builder()
                .property(property)
                .client(client)
                .build();

        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getClientFavorites(Long clientId) {
        return favoriteRepository.findByClientId(clientId);
    }

    @Transactional
    public void removeFavorite(Long propertyId, Long clientId) {
        favoriteRepository.deleteByPropertyIdAndClientId(propertyId, clientId);
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

