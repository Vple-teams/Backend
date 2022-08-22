package com.app.vple.domain.dto;

import com.app.vple.domain.Cart;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MyCartDto {

    private Long id;

    private String name;

    private String address;

    private String image;

    public MyCartDto(Cart entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.image = entity.getImage();
    }
}
