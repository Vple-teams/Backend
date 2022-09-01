package com.app.vple.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "area_code")
public class AreaCode {

    @Id
    private Long id;

    private String city;

    private String district;

    @Column(name = "city_code")
    private Long cityCode;

    @Column(name = "district_code")
    private Long districtCode;
}
