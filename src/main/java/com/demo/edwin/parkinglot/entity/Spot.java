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
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(name = "spot_id_previous")
    private Long spotIdPrevious;

    @Column(name = "spot_id_next")
    private Long spotIdNext;

    @Column
    private Boolean available;

    @JsonIgnore
    @ManyToMany(mappedBy = "spots")
    private Set<Vehicle> vehicles;
}
