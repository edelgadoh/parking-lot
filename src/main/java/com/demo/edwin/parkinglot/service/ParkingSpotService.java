package com.demo.edwin.parkinglot.service;

import com.demo.edwin.parkinglot.entity.*;
import com.demo.edwin.parkinglot.exception.ParkingSpotAlreadyExistException;
import com.demo.edwin.parkinglot.exception.SpotNotAvailableException;
import com.demo.edwin.parkinglot.exception.VehicleNotFoundException;
import com.demo.edwin.parkinglot.exception.VehicleNotFoundParkingSpotException;
import com.demo.edwin.parkinglot.repository.ParkingSpotRepository;
import com.demo.edwin.parkinglot.repository.SpotRepository;
import com.demo.edwin.parkinglot.repository.VehicleRepository;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@Slf4j
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
    public void takeInVehicle(VehicleDTO vehicleDTO) throws ParkingSpotAlreadyExistException, SpotNotAvailableException {

        Vehicle vehicle = getVehicle(vehicleDTO);
        validateParkingSpot(vehicle);

        List<Spot> availableSpots = spotService.getAvailableSpots(vehicle.getType());
        if (availableSpots.isEmpty()) {
            throw new SpotNotAvailableException(String.format("There is no available spots for Vehicle %s (%s)", vehicle.getCode(), vehicle.getType()));
        }
        for (Spot spot : availableSpots) {

            ParkingSpotKey parkingSpotKey = createParkingSpotKey(vehicle, spot);
            ParkingSpot parkingSpot = createParkingSpot(vehicle, spot, parkingSpotKey);
            parkingSpotRepository.save(parkingSpot);

            spot.setAvailable(FALSE);
            spotRepository.save(spot);
            log.info("Vehicle {} was take in with spot {}", vehicle, spot);
        }

    }

    private static ParkingSpot createParkingSpot(Vehicle vehicle, Spot spot, ParkingSpotKey parkingSpotKey) {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(parkingSpotKey);
        parkingSpot.setVehicle(vehicle);
        parkingSpot.setSpot(spot);
        parkingSpot.setType(vehicle.getType());
        return parkingSpot;
    }

    private static ParkingSpotKey createParkingSpotKey(Vehicle vehicle, Spot spot) {
        ParkingSpotKey parkingSpotKey = new ParkingSpotKey();
        parkingSpotKey.setVehicleId(vehicle.getId());
        parkingSpotKey.setSpotId(spot.getId());
        return parkingSpotKey;
    }

    private void validateParkingSpot(Vehicle vehicle) throws ParkingSpotAlreadyExistException {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVehicle(vehicle);
        if (!parkingSpots.isEmpty()) {
            throw new ParkingSpotAlreadyExistException(
                    String.format("Vehicle %s (%s) is already registered in %d spot(s)", vehicle.getCode(), vehicle.getType(), parkingSpots.size()));
        }
    }

    private Vehicle getVehicle(VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleService.getVehicle(vehicleDTO.getCode());
        return optionalVehicle.orElseGet(() -> vehicleService.saveVehicle(vehicleDTO));
    }

    @Transactional
    public void removeVehicle(String code) throws VehicleNotFoundException, VehicleNotFoundParkingSpotException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByCodeIgnoreCase(code);

        if (optionalVehicle.isPresent()) {

            Vehicle vehicle = optionalVehicle.get();
            List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVehicle(vehicle);
            if (parkingSpots.isEmpty())
                throw new VehicleNotFoundParkingSpotException(String.format("Vehicle %s has no parking spots", code));

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
