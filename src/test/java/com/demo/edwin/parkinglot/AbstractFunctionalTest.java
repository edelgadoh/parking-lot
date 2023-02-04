package com.demo.edwin.parkinglot;

import com.demo.edwin.parkinglot.repository.ParkingSpotRepository;
import com.demo.edwin.parkinglot.repository.SpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractFunctionalTest {

    @Autowired
    protected ParkingSpotRepository parkingSpotRepository;

    @Autowired
    protected SpotRepository spotRepository;

    //MOTORCYCLE, MOTORCYCLE, MOTORCYCLE, CAR, CAR, CAR, VAN, CAR, CAR, MOTORCYCLE, CAR, CAR, CAR, MOTORCYCLE
    protected int maxVanAvailableSpots = 3;
    protected int maxCarAvailableSpots = 9;
    protected int maxMotorcycleAvailableSpots = 14;

    @BeforeEach
    public void init() {
        parkingSpotRepository.deleteAll();
        spotRepository.findAll().stream().forEach(spot -> {
            spot.setAvailable(Boolean.TRUE);
            spotRepository.save(spot);
        });
    }

    @Autowired
    protected TestRestTemplate restTemplate;

}
