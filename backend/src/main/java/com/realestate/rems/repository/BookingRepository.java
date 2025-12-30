package com.realestate.rems.repository;

import com.realestate.rems.model.Booking;
import com.realestate.rems.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Booking Repository
 * Provides database access for Booking entity.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find all bookings by user ID
     */
    List<Booking> findByUserId(Long userId);

    /**
     * Find all bookings by property ID
     */
    List<Booking> findByPropertyId(Long propertyId);

    /**
     * Find all bookings by status
     */
    List<Booking> findByStatus(BookingStatus status);

    /**
     * Find booking by property and user
     */
    Optional<Booking> findByPropertyIdAndUserId(Long propertyId, Long userId);
}

