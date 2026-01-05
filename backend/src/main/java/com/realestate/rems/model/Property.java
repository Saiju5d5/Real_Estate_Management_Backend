package com.realestate.rems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotBlank(message = "Type is required")
    @Column(nullable = false)
    private String type; // 'rent' or 'buy'

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonIgnore
    private User agent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Default constructor
    public Property() {
    }

    // All-args constructor
    public Property(Long id, String title, String description, BigDecimal price,
            String location, String type, List<String> images, User agent, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.type = type;
        this.images = images != null ? images : new ArrayList<>();
        this.agent = agent;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public List<String> getImages() {
        return images;
    }

    public User getAgent() {
        return agent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static PropertyBuilder builder() {
        return new PropertyBuilder();
    }

    public static class PropertyBuilder {
        private Long id;
        private String title;
        private String description;
        private BigDecimal price;
        private String location;
        private String type;
        private List<String> images = new ArrayList<>();
        private User agent;
        private LocalDateTime createdAt;

        public PropertyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PropertyBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PropertyBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PropertyBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public PropertyBuilder location(String location) {
            this.location = location;
            return this;
        }

        public PropertyBuilder type(String type) {
            this.type = type;
            return this;
        }

        public PropertyBuilder images(List<String> images) {
            this.images = images;
            return this;
        }

        public PropertyBuilder agent(User agent) {
            this.agent = agent;
            return this;
        }

        public PropertyBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Property build() {
            return new Property(id, title, description, price, location, type, images, agent, createdAt);
        }
    }
}
