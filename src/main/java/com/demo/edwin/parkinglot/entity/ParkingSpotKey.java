package com.demo.edwin.parkinglot.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ParkingSpotKey implements Serializable {

    @Column(name = "vehicle_id")
    Long vehicleId;

    @Column(name = "spot_id")
    Long spotId;

}
