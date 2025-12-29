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

    @Transactional
    public List<Property> getAllProperties() {
        return propertyRepository.findAll(); // ✅ FIX
    }

    @Transactional
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
