package com.demo.edwin.parkinglot.service;

import com.demo.edwin.parkinglot.entity.*;
import com.demo.edwin.parkinglot.exception.ParkingSpotAlreadyExistException;
import com.demo.edwin.parkinglot.exception.SpotNotFoundException;
import com.demo.edwin.parkinglot.exception.VehicleNotFoundException;
import com.demo.edwin.parkinglot.repository.ParkingSpotRepository;
import com.demo.edwin.parkinglot.repository.SpotRepository;
import com.demo.edwin.parkinglot.repository.VehicleRepository;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class ParkingSpotService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private SpotService spotService;

    @Autowired
    private VehicleService vehicleService;

    @Transactional
    public void takeInVehicle(VehicleDTO vehicleDTO) throws ParkingSpotAlreadyExistException, SpotNotFoundException {

        Vehicle vehicle = getVehicle(vehicleDTO);
        validateParkingSpot(vehicle);

        List<Spot> availableSpots = spotService.getAvailableSpots(vehicle.getType());
        if (availableSpots.isEmpty()) {
            throw new SpotNotFoundException(String.format("There is no available spots for Vehicle %s", vehicle.getCode()));
        }
        for (Spot spot : availableSpots) {

            ParkingSpotKey parkingSpotKey = new ParkingSpotKey();
            parkingSpotKey.setVehicleId(vehicle.getId());
            parkingSpotKey.setSpotId(spot.getId());

            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setId(parkingSpotKey);
            parkingSpot.setVehicle(vehicle);
            parkingSpot.setSpot(spot);
            parkingSpot.setType(vehicle.getType());
            parkingSpotRepository.save(parkingSpot);

            spot.setAvailable(FALSE);
            spotRepository.save(spot);
        }

    }

    private void validateParkingSpot(Vehicle vehicle) throws ParkingSpotAlreadyExistException {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVehicle(vehicle);
        if (!parkingSpots.isEmpty()) {
            throw new ParkingSpotAlreadyExistException(
                    String.format("Vehicle %s is already registered in %d spot(s)", vehicle.getCode(), parkingSpots.size()));
        }
    }

    private Vehicle getVehicle(VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleService.getVehicle(vehicleDTO.getCode());
        if (optionalVehicle.isPresent()) {
            return optionalVehicle.get();
        } else {
            return vehicleService.saveVehicle(vehicleDTO);
        }
    }

    @Transactional
    public void removeVehicle(String code) throws VehicleNotFoundException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByCodeIgnoreCase(code);

        if (optionalVehicle.isPresent()) {

            Vehicle vehicle = optionalVehicle.get();
            List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVehicle(vehicle);
            parkingSpots.stream().forEach(parkingSpot -> {
                parkingSpot.getSpot().setAvailable(TRUE);
                parkingSpotRepository.delete(parkingSpot);
            });

        } else {
            throw new VehicleNotFoundException(code);
        }
    }

    public Long findSpots(VehicleType vehicleType) {
        return parkingSpotRepository.findByType(vehicleType).stream().count();
    }

}
