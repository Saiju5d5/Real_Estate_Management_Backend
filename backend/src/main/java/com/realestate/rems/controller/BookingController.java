package com.realestate.rems.controller;

import com.realestate.rems.model.ApiResponse;
import com.realestate.rems.model.Booking;
import com.realestate.rems.model.BookingStatus;
import com.realestate.rems.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Booking Controller
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * Create a new booking (CUSTOMER, AGENT, OWNER)
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER','OWNER')")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(createdBooking);
    }

    /**
     * Get all bookings (ADMIN, AGENT)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /**
     * Get booking by ID (ADMIN, AGENT, or booking owner)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CUSTOMER','OWNER')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    /**
     * Get bookings by user ID (ADMIN, AGENT)
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    /**
     * Get bookings by property ID (ADMIN, AGENT, OWNER)
     */
    @GetMapping("/property/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','OWNER')")
    public ResponseEntity<List<Booking>> getBookingsByPropertyId(@PathVariable Long propertyId) {
        return ResponseEntity.ok(bookingService.getBookingsByPropertyId(propertyId));
    }

    /**
     * Get bookings by status (ADMIN, AGENT)
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    /**
     * Update booking status (ADMIN, AGENT)
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * Update booking (ADMIN, AGENT, or booking owner)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * Delete booking (ADMIN, AGENT)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        ApiResponse response = new ApiResponse(true, "Booking deleted successfully");
        return ResponseEntity.ok(response);
    }
}

