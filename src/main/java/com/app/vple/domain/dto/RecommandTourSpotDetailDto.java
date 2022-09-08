package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.enums.TourType;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecommandTourSpotDetailDto {

    private Long id;

    private String name;

    private String latitude;

    private String longitude;

    private String address;

    private float rating;

    private String image;

    private List<ReviewDto> reviews;

    private HashTagDto hashTags;

    private Integer reivewCount;

    public RecommandTourSpotDetailDto(RecommandTourSpot entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.rating = entity.getRating();
        this.image = entity.getImage();
        this.reviews = entity.getReviews().stream().map(
                ReviewDto::new
        ).collect(Collectors.toList());
        this.reivewCount = entity.getReviewCount();
        this.hashTags = new HashTagDto(entity.getReviews());
    }

}