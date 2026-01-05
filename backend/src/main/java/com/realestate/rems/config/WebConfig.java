package com.realestate.rems.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                // Serve uploaded images from uploads/images folder
                // When running from backend folder, this resolves correctly
                Path uploadPath = Paths.get("uploads/images").toAbsolutePath();
                String uploadLocation = "file:///" + uploadPath.toString().replace('\\', '/') + "/";

                System.out.println("Image upload path configured: " + uploadLocation);

                registry.addResourceHandler("/uploads/images/**")
                                .addResourceLocations(uploadLocation);

                // Frontend static resources
                // When running from backend folder, frontend is at ../frontend
                Path frontendPath = Paths.get("../frontend/static").toAbsolutePath().normalize();
                Path backendStaticPath = Paths.get("src/main/resources/static").toAbsolutePath();

                String frontendLocation = "file:///" + frontendPath.toString().replace('\\', '/') + "/";
                String backendStaticLocation = "file:///" + backendStaticPath.toString().replace('\\', '/') + "/";

                registry.addResourceHandler("/css/**")
                                .addResourceLocations(
                                                frontendLocation + "css/",
                                                backendStaticLocation + "css/",
                                                "classpath:/static/css/");

                registry.addResourceHandler("/js/**")
                                .addResourceLocations(
                                                frontendLocation + "js/",
                                                backendStaticLocation + "js/",
                                                "classpath:/static/js/");

                registry.addResourceHandler("/images/**")
                                .addResourceLocations(
                                                frontendLocation + "images/",
                                                backendStaticLocation + "images/",
                                                "classpath:/static/images/");
        }
}
