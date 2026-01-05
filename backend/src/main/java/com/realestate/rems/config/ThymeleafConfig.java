package com.realestate.rems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ThymeleafConfig {

    /**
     * Custom template resolver that prioritizes frontend folder,
     * falls back to classpath if frontend folder doesn't exist
     */
    @Bean
    @Primary
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        
        // Check if frontend folder exists, prioritize it
        String frontendTemplatesPath = Paths.get("frontend/templates").toAbsolutePath().toString();
        
        if (Files.exists(Paths.get(frontendTemplatesPath))) {
            // Use frontend folder (absolute path)
            resolver.setPrefix("file:" + frontendTemplatesPath + "/");
        } else {
            // Fallback to classpath (backend resources)
            resolver.setPrefix("classpath:/templates/");
        }
        
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false); // Disable cache in development
        
        return resolver;
    }
}

