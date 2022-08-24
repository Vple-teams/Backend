package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "mates")
@Builder
@Getter
@AllArgsConstructor
public class Mate {

    public Mate() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false, name = "city_district")
    private String cityDistrict;

    @Column(nullable = false, name = "dong")
    private String dong;
}
