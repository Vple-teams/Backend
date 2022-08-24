package com.app.vple.domain.dto;

import com.app.vple.domain.Cart;
import lombok.Data;

@Data
public class CartDetailDto {

    private String name;

    private String address;

    private String longitude;

    private String latitude;

    private String image;

    public CartDetailDto(Cart entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.longitude = entity.getLongitude();
        this.latitude = entity.getLatitude();
        this.image = entity.getImage();
    }
}
