package com.realestate.rems.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity   // 🔥 REQUIRED for @PreAuthorize
public class MethodSecurityConfig {
}
