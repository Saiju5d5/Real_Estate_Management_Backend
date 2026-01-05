package com.realestate.rems.controller;

import com.realestate.rems.model.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ImageUploadController {

    // Upload directory - use 'uploads/images' relative to working directory (which
    // is 'backend' folder)
    private static final String UPLOAD_DIR = "uploads/images";

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "File is empty"));
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "File must be an image"));
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // Get absolute path for uploads directory
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();

            // Create upload directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return URL (this is the URL path, not file system path)
            String imageUrl = "/uploads/images/" + filename;

            return ResponseEntity.ok(new ApiResponse(true, imageUrl));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Failed to upload image: " + e.getMessage()));
        }
    }
}
