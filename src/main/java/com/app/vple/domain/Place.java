package com.app.vple.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String introduction;

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

    @PrePersist
    public void prePersist() {
        this.rating = this.rating == null ? 0 : this.rating;
    }

    public void changeRating(float rating) {
        this.rating += rating;
    }
}
