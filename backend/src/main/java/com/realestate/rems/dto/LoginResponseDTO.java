package com.realestate.rems.dto;

import java.util.Set;

public class LoginResponseDTO {

    private String token;
    private String email;
    private Set<String> roles;

    public LoginResponseDTO(String token, String email, Set<String> roles) {
        this.token = token;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
