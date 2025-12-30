package com.realestate.rems.dto;

import java.util.Set;

public class LoginResponseDTO {

    private String token;
    private String email;
    private Set<String> roles;
    private Long userId;

    // No-arg constructor for JSON deserialization
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String email, Set<String> roles) {
        this.token = token;
        this.email = email;
        this.roles = roles;
    }

    public LoginResponseDTO(String token, String email, Set<String> roles, Long userId) {
        this.token = token;
        this.email = email;
        this.roles = roles;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
