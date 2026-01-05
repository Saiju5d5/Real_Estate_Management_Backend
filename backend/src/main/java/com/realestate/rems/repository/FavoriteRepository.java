package com.realestate.rems.repository;

import com.realestate.rems.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByClientId(Long clientId);
    Optional<Favorite> findByPropertyIdAndClientId(Long propertyId, Long clientId);
    boolean existsByPropertyIdAndClientId(Long propertyId, Long clientId);
    void deleteByPropertyIdAndClientId(Long propertyId, Long clientId);
}

