package com.realestate.rems.model;

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
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Property being booked for visit
     */
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /**
     * User who booked the visit
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Visit date is required")
    private LocalDate visitDate;

    /**
     * Booking status:
     * PENDING, APPROVED, REJECTED, COMPLETED
     */
    @Column(nullable = false)
    private String status;
}
