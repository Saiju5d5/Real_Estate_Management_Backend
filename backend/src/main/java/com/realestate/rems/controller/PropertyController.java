package com.realestate.rems.controller;

import com.realestate.rems.model.ApiResponse;
import com.realestate.rems.model.Property;
import com.realestate.rems.service.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    /**
     * Create property (agent only)
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_agent')")
    public ResponseEntity<Property> addProperty(
            @Valid @RequestBody Property property,
            Authentication authentication) {
        Long agentId = getUserIdFromAuthentication(authentication);
        Property savedProperty = propertyService.addProperty(property, agentId);
        return ResponseEntity.ok(savedProperty);
    }

    /**
     * Get all properties with optional search/filter
     */
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String type) {
        
        if (search != null || minPrice != null || maxPrice != null || type != null) {
            return ResponseEntity.ok(propertyService.searchProperties(search, minPrice, maxPrice, type));
        }
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    /**
     * Get property by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    /**
     * Get properties by agent ID
     */
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Property>> getPropertiesByAgentId(@PathVariable Long agentId) {
        return ResponseEntity.ok(propertyService.getPropertiesByAgentId(agentId));
    }

    /**
     * Update property (agent only - own properties)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_agent')")
    public ResponseEntity<Property> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody Property property,
            Authentication authentication) {
        Long agentId = getUserIdFromAuthentication(authentication);
        Property updatedProperty = propertyService.updateProperty(id, property, agentId);
        return ResponseEntity.ok(updatedProperty);
    }

    /**
     * Delete property (agent only - own properties)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_agent')")
    public ResponseEntity<ApiResponse> deleteProperty(
            @PathVariable Long id,
            Authentication authentication) {
        Long agentId = getUserIdFromAuthentication(authentication);
        propertyService.deleteProperty(id, agentId);
        ApiResponse response = new ApiResponse(true, "Property deleted successfully");
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return propertyService.getUserIdByEmail(email);
    }
}
