package com.demo.edwin.parkinglot.controller;

import com.demo.edwin.parkinglot.AbstractFunctionalTest;
import com.demo.edwin.parkinglot.entity.VehicleType;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class ParkingSpotControllerTest extends AbstractFunctionalTest {

    @Test
    void givenNewVehicle_validateParkingSpot() {

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode("XP-123456");
        vehicleDTO.setType(VehicleType.MOTORCYCLE);

        ResponseEntity<?> responseEntityParkingSpot = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.OK, responseEntityParkingSpot.getStatusCode());

        ResponseEntity<?> responseEntityNotAllowed = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntityNotAllowed.getStatusCode());

    }

    @Test
    void givenAlreadyCreatedVehicle_validateParkingSpot() {

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode("BR-45871");
        vehicleDTO.setType(VehicleType.MOTORCYCLE);

        ResponseEntity<?> responseEntityParkingSpot = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.OK, responseEntityParkingSpot.getStatusCode());

    }

    @Test
    void givenPredefinedSpots_validateVansParkingSpot() {

        for (int i = 0; i < maxVanAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.VAN);

            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode(RandomStringUtils.random(10));
        vehicleDTO.setType(VehicleType.VAN);

        ResponseEntity<?> lastResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, lastResponseEntity.getStatusCode());

    }

    @Test
    void givenPredefinedSpots_validateCarsParkingSpot() {

        for (int i = 0; i < maxCarAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.CAR);

            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode(RandomStringUtils.random(10));
        vehicleDTO.setType(VehicleType.CAR);

        ResponseEntity<?> lastResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, lastResponseEntity.getStatusCode());

    }

    @Test
    void givenPredefinedSpots_validateMotorcycleParkingSpot() {

        for (int i = 0; i < maxMotorcycleAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.MOTORCYCLE);

            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode(RandomStringUtils.random(10));
        vehicleDTO.setType(VehicleType.MOTORCYCLE);

        ResponseEntity<?> lastResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, lastResponseEntity.getStatusCode());

    }

    @Test
    void givenPredefinedSpots_takeInAndRemoveVans_AndValidateSpotQuantities() {

        ResponseEntity<Long> initialResponseEntity = restTemplate.getForEntity("/parking-spots/vehicle-type/VAN",  Long.class);
        Assertions.assertEquals(HttpStatus.OK, initialResponseEntity.getStatusCode());
        Assertions.assertEquals(0, initialResponseEntity.getBody());

        List<String> vehicleCodes = new ArrayList<>();

        for (int i = 0; i < maxVanAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.VAN);

            vehicleCodes.add(vehicleDTO.getCode());

            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("/parking-spots/vehicle-type/VAN",  Long.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(7, responseEntity.getBody());

        for(String code: vehicleCodes){
            restTemplate.delete("/parking-spots/vehicle-code/"+code);
        }

        ResponseEntity<Long> finalResponseEntity = restTemplate.getForEntity("/parking-spots/vehicle-type/VAN",  Long.class);
        Assertions.assertEquals(HttpStatus.OK, finalResponseEntity.getStatusCode());
        Assertions.assertEquals(0, finalResponseEntity.getBody());

    }

}