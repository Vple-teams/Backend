package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.enums.VeganType;

import lombok.Data;

@Data
public class RecommandRestaurantDetailDto {

    private Long id;
    private String name;
    private String latitude;
    private String longitude;
    private String profile;
    private float rating;
    private String image;
    private VeganType veganType;

    public RecommandRestaurantDetailDto(RecommandRestaurant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.profile = entity.getProfile();
        this.rating = entity.getRating();
        this.image = entity.getImage();
        this.veganType = entity.getVeganType();
    }
}