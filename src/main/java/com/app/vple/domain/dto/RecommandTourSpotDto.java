package com.app.vple.domain.dto;

import com.app.vple.service.model.TourSpot.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecommandTourSpotDto {

    @JsonProperty(value = "address")
    private String addr1;

    @JsonProperty(value = "image1")
    private String firstimage;

    @JsonProperty(value = "image2")
    private String firstimage2;

    @JsonProperty(value = "longitude")
    private String mapx;

    @JsonProperty(value = "latitude")
    private String mapy;

    private String tel;

    private String title;

    public RecommandTourSpotDto(Item item) {
        this.addr1 = item.addr1;
        this.firstimage = item.firstimage;
        this.firstimage2 = item.firstimage2;
        this.mapx = item.mapx;
        this.mapy = item.mapy;
        this.tel = item.tel;
        this.title = item.title;
    }

}
