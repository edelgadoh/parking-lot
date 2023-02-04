package com.demo.edwin.parkinglot.service;

import com.demo.edwin.parkinglot.entity.Vehicle;
import com.demo.edwin.parkinglot.repository.VehicleRepository;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Optional<Vehicle> getVehicle(String code) {
        return vehicleRepository.findByCodeIgnoreCase(code);
    }

    @Transactional
    public Vehicle saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setCode(vehicleDTO.getCode());
        vehicle.setType(vehicleDTO.getType());
        return vehicleRepository.save(vehicle);
    }

}
