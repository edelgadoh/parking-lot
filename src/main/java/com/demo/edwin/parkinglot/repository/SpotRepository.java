package com.demo.edwin.parkinglot.repository;

import com.demo.edwin.parkinglot.entity.Spot;
import com.demo.edwin.parkinglot.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByAvailable(boolean available);
    Optional<Spot> findBySpotIdNext(Long spotIdNext);
    Optional<Spot> findFirstByAvailableAndType(boolean available, VehicleType vehicleType);
    List<Spot> findByAvailableAndType(boolean available, VehicleType vehicleType);

}
