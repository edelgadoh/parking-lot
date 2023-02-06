package com.demo.edwin.parkinglot.service;

import com.demo.edwin.parkinglot.config.VehicleTypeConfig;
import com.demo.edwin.parkinglot.entity.Spot;
import com.demo.edwin.parkinglot.entity.VehicleType;
import com.demo.edwin.parkinglot.repository.SpotRepository;
import com.demo.edwin.parkinglot.request.SpotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private VehicleTypeConfig vehicleTypeConfig;

    public Long getAllSpots(Boolean available) {
        return spotRepository.findByAvailable(available).stream().count();
    }

    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    public Spot saveSpot(SpotDTO spotDTO) {

        Optional<Spot> previousSpot = spotRepository.findBySpotIdNext(null);

        Spot newSpot = new Spot();
        newSpot.setCode(spotDTO.getCode());
        newSpot.setType(spotDTO.getType());
        newSpot.setAvailable(TRUE);
        newSpot.setSpotIdPrevious(null);
        newSpot.setSpotIdNext(null);
        Spot spotSaved = spotRepository.save(newSpot);

        if (previousSpot.isPresent()) {
            Spot spot = previousSpot.get();
            spot.setSpotIdNext(spotSaved.getId());
            spotRepository.save(spot);
            spotSaved.setSpotIdPrevious(spot.getId());
            spotRepository.save(spotSaved);
        }

        return spotSaved;
    }

    public List<Spot> getAvailableSpots(VehicleType vehicleType) {

        //General rule
        Optional<Spot> spots = spotRepository.findFirstByAvailableAndType(TRUE, vehicleType);
        if (spots.isPresent()) {
            return List.of(spots.get());
        }

        if (vehicleType.equals(VehicleType.VAN)) {
            //Check if exists 3 adjacent spots of CAR type and are available
            List<Spot> spotList = spotRepository.findByAvailableAndType(TRUE, VehicleType.CAR);
            for (Spot spot : spotList) {
                Spot previous = getSpot(spot.getSpotIdPrevious());
                Spot next = getSpot(spot.getSpotIdNext());
                if (isAvailableSpot(previous, VehicleType.CAR) && isAvailableSpot(next, VehicleType.CAR)) {
                    return List.of(spot, previous, next);
                }
            }

        } else {
            //Custom Business Rule for spot types
            List<VehicleType> extraVehicleTypesToSearch = vehicleTypeConfig.getConfig().get(vehicleType);
            for (VehicleType vehicleTypeToSearch : extraVehicleTypesToSearch) {
                Optional<Spot> optionalSpot = spotRepository.findFirstByAvailableAndType(TRUE, vehicleTypeToSearch);
                if (optionalSpot.isPresent()) {
                    return List.of(optionalSpot.get());
                }
            }

        }

        return List.of();
    }

    private Spot getSpot(Long spotId) {
        return spotRepository.findById(spotId).get();
    }

    private boolean isAvailableSpot(Spot spot, VehicleType vehicleType) {
        return spot != null && spot.getType().equals(vehicleType) && spot.getAvailable();
    }


}
