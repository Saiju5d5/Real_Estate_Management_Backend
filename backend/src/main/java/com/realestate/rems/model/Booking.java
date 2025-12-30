package com.realestate.rems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Booking entity represents property visit bookings.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Property being booked for visit
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    @NotNull(message = "Property is required")
    @JsonIgnoreProperties({"owner", "agent"})
    private Property property;

    /**
     * User who booked the visit
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    @JsonIgnoreProperties({"password", "roles"})
    private User user;

    @NotNull(message = "Visit date is required")
    @Column(nullable = false)
    private LocalDate visitDate;

    /**
     * Booking status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Booking status is required")
    private BookingStatus status;
}
