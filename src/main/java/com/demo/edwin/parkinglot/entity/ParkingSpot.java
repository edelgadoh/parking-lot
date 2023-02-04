package com.demo.edwin.parkinglot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "parking_spot")
@Getter
@Setter
@ToString
public class ParkingSpot {

    @EmbeddedId
    ParkingSpotKey id;

    @ManyToOne
    @MapsId("vehicleId")
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne
    @MapsId("spotId")
    @JoinColumn(name = "spot_id")
    Spot spot;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;


}
