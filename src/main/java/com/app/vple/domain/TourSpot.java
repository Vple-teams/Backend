package com.app.vple.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class TourSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_spot_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;
}
