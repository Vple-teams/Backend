package com.app.vple.domain;

import com.app.vple.domain.enums.VeganType;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String introduction;

    private String phoneNumber;

    private String openTime;

    private Float rating;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    private String image;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus;

    @Enumerated(EnumType.STRING)
    private VeganType veganType;

}
