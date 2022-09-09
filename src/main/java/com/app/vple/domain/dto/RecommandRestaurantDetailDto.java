package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.enums.VeganType;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecommandRestaurantDetailDto {

    private Long id;

    private String name;

    private String category;

    private String introduction;

    private String phoneNumber;

    private String openTime;

    private String address;

    private String district;

    private String city;

    private String latitude;

    private String longitude;

    private List<MenuDto> menus;

    private float rating;

    private String image;

    private VeganType veganType;

    private List<ReviewDto> reviews;

    private HashTagDto hashTags;

    public RecommandRestaurantDetailDto(RecommandRestaurant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.introduction = entity.getIntroduction();
        this.phoneNumber = entity.getPhoneNumber();
        this.openTime = entity.getOpenTime();
        this.address = entity.getAddress();
        this.district = entity.getDistrict();
        this.city = entity.getCity();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.menus = entity.getMenus().stream().map(
                MenuDto::new
        ).collect(Collectors.toList());
        this.rating = entity.getRating();
        this.image = entity.getImage();
        this.veganType = entity.getVeganType();
        this.reviews = entity.getReviews().stream().map(ReviewDto::new).collect(Collectors.toList());
        this.hashTags = new HashTagDto(entity.getReviews(), false);
    }
}