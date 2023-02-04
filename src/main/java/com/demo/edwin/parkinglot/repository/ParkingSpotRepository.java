package com.demo.edwin.parkinglot.repository;

import com.demo.edwin.parkinglot.entity.ParkingSpot;
import com.demo.edwin.parkinglot.entity.ParkingSpotKey;
import com.demo.edwin.parkinglot.entity.Vehicle;
import com.demo.edwin.parkinglot.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, ParkingSpotKey> {

    List<ParkingSpot> findByType(VehicleType vehicleType);

    List<ParkingSpot> findByVehicle(Vehicle vehicle);
}
