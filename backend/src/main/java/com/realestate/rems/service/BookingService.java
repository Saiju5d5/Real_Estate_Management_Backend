package com.realestate.rems.service;

import com.realestate.rems.exception.ResourceNotFoundException;
import com.realestate.rems.model.Booking;
import com.realestate.rems.model.BookingStatus;
import com.realestate.rems.model.Property;
import com.realestate.rems.model.User;
import com.realestate.rems.repository.BookingRepository;
import com.realestate.rems.repository.PropertyRepository;
import com.realestate.rems.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Booking Service
 * Contains business logic related to bookings.
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new booking
     */
    @Transactional
    public Booking createBooking(Booking booking) {
        // Validate property exists
        Property property = propertyRepository.findById(booking.getProperty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + booking.getProperty().getId()));

        // Validate user exists
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + booking.getUser().getId()));

        // Set default status if not provided
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        booking.setProperty(property);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    /**
     * Get all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Get booking by ID
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    /**
     * Get bookings by user ID
     */
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    /**
     * Get bookings by property ID
     */
    public List<Booking> getBookingsByPropertyId(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }

    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    /**
     * Update booking status
     */
    @Transactional
    public Booking updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    /**
     * Update booking
     */
    @Transactional
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = getBookingById(id);

        if (bookingDetails.getVisitDate() != null) {
            booking.setVisitDate(bookingDetails.getVisitDate());
        }
        if (bookingDetails.getStatus() != null) {
            booking.setStatus(bookingDetails.getStatus());
        }
        if (bookingDetails.getProperty() != null && bookingDetails.getProperty().getId() != null) {
            Property property = propertyRepository.findById(bookingDetails.getProperty().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
            booking.setProperty(property);
        }

        return bookingRepository.save(booking);
    }

    /**
     * Delete booking
     */
    @Transactional
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}

