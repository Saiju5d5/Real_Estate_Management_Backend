package com.realestate.rems.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "property_id", "client_id" })
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Default constructor
    public Favorite() {
    }

    // All-args constructor
    public Favorite(Long id, Property property, User client, LocalDateTime createdAt) {
        this.id = id;
        this.property = property;
        this.client = client;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Property getProperty() {
        return property;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static FavoriteBuilder builder() {
        return new FavoriteBuilder();
    }

    public static class FavoriteBuilder {
        private Long id;
        private Property property;
        private User client;
        private LocalDateTime createdAt;

        public FavoriteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FavoriteBuilder property(Property property) {
            this.property = property;
            return this;
        }

        public FavoriteBuilder client(User client) {
            this.client = client;
            return this;
        }

        public FavoriteBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Favorite build() {
            return new Favorite(id, property, client, createdAt);
        }
    }
}
