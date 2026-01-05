package com.realestate.rems.service;

import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.Property;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.PropertyRepository;
import com.realestate.rems.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Property addProperty(Property property, Long agentId) {
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + agentId));
        
        if (!"agent".equals(agent.getRole())) {
            throw new IllegalArgumentException("User must be an agent to create properties");
        }
        
        property.setAgent(agent);
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> searchProperties(String search, BigDecimal minPrice, BigDecimal maxPrice, String type) {
        return propertyRepository.searchProperties(search, minPrice, maxPrice, type);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    public List<Property> getPropertiesByAgentId(Long agentId) {
        return propertyRepository.findByAgentId(agentId);
    }

    @Transactional
    public Property updateProperty(Long id, Property propertyDetails, Long agentId) {
        Property property = getPropertyById(id);
        
        // Verify agent owns this property
        if (!property.getAgent().getId().equals(agentId)) {
            throw new IllegalArgumentException("You can only update your own properties");
        }
        
        // Update fields if provided
        if (propertyDetails.getTitle() != null && !propertyDetails.getTitle().isEmpty()) {
            property.setTitle(propertyDetails.getTitle());
        }
        if (propertyDetails.getDescription() != null) {
            property.setDescription(propertyDetails.getDescription());
        }
        if (propertyDetails.getPrice() != null) {
            property.setPrice(propertyDetails.getPrice());
        }
        if (propertyDetails.getLocation() != null && !propertyDetails.getLocation().isEmpty()) {
            property.setLocation(propertyDetails.getLocation());
        }
        if (propertyDetails.getType() != null && !propertyDetails.getType().isEmpty()) {
            property.setType(propertyDetails.getType());
        }
        if (propertyDetails.getImages() != null) {
            property.setImages(propertyDetails.getImages());
        }
        
        return propertyRepository.save(property);
    }

    @Transactional
    public void deleteProperty(Long id, Long agentId) {
        Property property = getPropertyById(id);
        
        // Verify agent owns this property
        if (!property.getAgent().getId().equals(agentId)) {
            throw new IllegalArgumentException("You can only delete your own properties");
        }
        
        propertyRepository.deleteById(id);
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
