package com.realestate.rems.controller;

import com.realestate.rems.model.ApiResponse;
import com.realestate.rems.model.Property;
import com.realestate.rems.service.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Property Controller
 */
@RestController
@RequestMapping("/api/properties")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth") // Swagger JWT
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    /**
     * Add property (ADMIN only)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Property> addProperty(@Valid @RequestBody Property property) {
        Property savedProperty = propertyService.addProperty(property);
        return ResponseEntity.ok(savedProperty);
    }

    /**
     * Get all properties (ALL logged-in users)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER')")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    /**
     * Get property by ID (ALL logged-in users)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER')")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    /**
     * Update property (ADMIN only)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Valid @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }

    /**
     * Delete property (ADMIN only)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        ApiResponse response = new ApiResponse(true, "Property deleted successfully");
        return ResponseEntity.ok(response);
    }
}
