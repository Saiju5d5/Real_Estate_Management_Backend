package com.realestate.rems.repository;

import com.realestate.rems.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Property Repository
 * Provides database access for Property entity.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Custom queries can be added later if needed
}
