package com.realestate.rems.service;

import com.realestate.rems.model.Property;
import com.realestate.rems.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new com.realestate.rems.exception.ResourceNotFoundException("Property not found with id: " + id));
    }

    @Transactional
    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = getPropertyById(id);
        
        // Update fields if provided
        if (propertyDetails.getTitle() != null && !propertyDetails.getTitle().isEmpty()) {
            property.setTitle(propertyDetails.getTitle());
        }
        if (propertyDetails.getCity() != null && !propertyDetails.getCity().isEmpty()) {
            property.setCity(propertyDetails.getCity());
        }
        if (propertyDetails.getPrice() != null) {
            property.setPrice(propertyDetails.getPrice());
        }
        if (propertyDetails.getType() != null) {
            property.setType(propertyDetails.getType());
        }
        if (propertyDetails.getStatus() != null) {
            property.setStatus(propertyDetails.getStatus());
        }
        if (propertyDetails.getOwner() != null) {
            property.setOwner(propertyDetails.getOwner());
        }
        if (propertyDetails.getAgent() != null) {
            property.setAgent(propertyDetails.getAgent());
        }
        
        return propertyRepository.save(property);
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
