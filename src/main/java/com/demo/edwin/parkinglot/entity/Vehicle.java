package com.demo.edwin.parkinglot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "parking_spot",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "spot_id"))
    private Set<Spot> spots;
}
