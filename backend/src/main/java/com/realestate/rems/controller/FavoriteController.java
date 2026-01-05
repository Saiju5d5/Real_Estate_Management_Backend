package com.realestate.rems.controller;

import com.realestate.rems.model.ApiResponse;
import com.realestate.rems.model.Favorite;
import com.realestate.rems.service.FavoriteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{propertyId}")
    @PreAuthorize("hasRole('ROLE_client')")
    public ResponseEntity<Favorite> addFavorite(
            @PathVariable Long propertyId,
            Authentication authentication) {
        Long clientId = getUserIdFromAuthentication(authentication);
        Favorite favorite = favoriteService.addFavorite(propertyId, clientId);
        return ResponseEntity.ok(favorite);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_client')")
    public ResponseEntity<List<Favorite>> getFavorites(Authentication authentication) {
        Long clientId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(favoriteService.getClientFavorites(clientId));
    }

    @DeleteMapping("/{propertyId}")
    @PreAuthorize("hasRole('ROLE_client')")
    public ResponseEntity<ApiResponse> removeFavorite(
            @PathVariable Long propertyId,
            Authentication authentication) {
        Long clientId = getUserIdFromAuthentication(authentication);
        favoriteService.removeFavorite(propertyId, clientId);
        ApiResponse response = new ApiResponse(true, "Favorite removed successfully");
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return favoriteService.getUserIdByEmail(email);
    }
}

