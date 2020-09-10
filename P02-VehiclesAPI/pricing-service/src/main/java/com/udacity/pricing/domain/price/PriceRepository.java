package com.udacity.pricing.domain.price;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByVehicleId(Long vehicleId);

    //    @Transactional
    void deleteByVehicleId(Long vehicleId);
}
