package com.realestate.rems.controller;

import com.realestate.rems.model.Property;
import com.realestate.rems.service.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Property Controller
 */
@RestController
@RequestMapping("/api/properties")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")   // Swagger JWT
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    /**
     * Add property (ADMIN only)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Property addProperty(@RequestBody Property property) {
        return propertyService.addProperty(property);
    }

    /**
     * Get all properties (ALL logged-in users)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER')")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    /**
     * Get property by ID (ALL logged-in users)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER')")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    /**
     * Delete property (ADMIN only)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "Property deleted successfully";
    }
}
