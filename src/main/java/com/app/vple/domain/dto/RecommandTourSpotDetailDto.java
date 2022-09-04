package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.enums.TourType;

import lombok.Data;

@Data
public class RecommandTourSpotDetailDto {

    private Long id;

    private String name;

    private String latitude;

    private String longitude;

    private String profile;

    private String address;

    private float rating;

    private String image;

    private TourType tourType;

    public RecommandTourSpotDetailDto(RecommandTourSpot entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.profile = entity.getProfile();
        this.address = entity.getAddress();
        this.rating = entity.getRating();
        this.image = entity.getImage();
        this.tourType = entity.getTourType();
    }
}