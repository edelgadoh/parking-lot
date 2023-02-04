package com.demo.edwin.parkinglot.repository;

import com.demo.edwin.parkinglot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByCodeIgnoreCase(String code);

}
