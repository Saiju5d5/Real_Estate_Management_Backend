package com.realestate.rems.dto;

import com.realestate.rems.model.BookingStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO for creating/updating bookings
 */
public class BookingRequestDTO {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Visit date is required")
    private LocalDate visitDate;

    private BookingStatus status;

    public BookingRequestDTO() {
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

