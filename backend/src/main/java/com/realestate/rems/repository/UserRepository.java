package com.realestate.rems.repository;

import com.realestate.rems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 * Provides database access for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);
}
