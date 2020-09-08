package com.udacity.pricing.domain.price;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByVehicleId(Long vehicleId);

    @Override
    @RestResource(exported = false)
    Optional<Price> findById(Long aLong);

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

    @Transactional
    void deleteByVehicleId(Long vehicleId);
}
