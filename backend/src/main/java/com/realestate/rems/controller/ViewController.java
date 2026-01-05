package com.realestate.rems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Public pages
    @GetMapping("/")
    public String landing() {
        return "public/landing";
    }

    // Authentication pages
    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "auth/register";
    }

    // Client pages
    @GetMapping("/client/dashboard")
    public String clientDashboard() {
        return "client/dashboard";
    }

    @GetMapping("/client/favorites")
    public String clientFavorites() {
        return "client/favorites";
    }

    @GetMapping("/client/profile")
    public String clientProfile() {
        return "client/profile";
    }

    // Agent pages
    @GetMapping("/agent/dashboard")
    public String agentDashboard() {
        return "agent/dashboard";
    }

    @GetMapping("/agent/add-property")
    public String addProperty() {
        return "agent/add-property";
    }

    @GetMapping("/agent/update-property")
    public String updateProperty() {
        return "agent/update-property";
    }

    @GetMapping("/agent/my-properties")
    public String myProperties() {
        return "agent/my-properties";
    }

    @GetMapping("/agent/profile")
    public String agentProfile() {
        return "agent/profile";
    }

    // Property pages
    @GetMapping("/property/details")
    public String propertyDetails() {
        return "property/details";
    }
}

