package com.app.vple.domain.dto;

import com.app.vple.domain.Cart;
import com.app.vple.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CartAddDto {

    @NotBlank(message = "장소 이름이 필요합니다.")
    private String name;

    @NotNull(message = "주소가 필요합니다.")
    private String address;

    @NotNull(message = "경도가 필요합니다.")
    private String longitude;

    @NotNull(message = "위도가 필요합니다.")
    private String latitude;

    private String image;

    public Cart toEntity(User user) {
        return Cart.builder()
                .name(name)
                .address(address)
                .longitude(longitude)
                .latitude(latitude)
                .user(user)
                .build();
    }

}
