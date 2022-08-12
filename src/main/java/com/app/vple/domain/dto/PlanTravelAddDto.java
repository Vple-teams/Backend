package com.app.vple.domain.dto;

import com.app.vple.domain.Plan;
import com.app.vple.domain.PlanTravel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlanTravelAddDto {

    @NotBlank(message = "장소 이름이 필요합니다.")
    private String name;

    private Long planId;

    @NotNull(message = "주소가 필요합니다.")
    private String address;

    @NotNull(message = "경도가 필요합니다.")
    private String longitude;

    @NotNull(message = "위도가 필요합니다.")
    private String latitude;

    private String image;

    public PlanTravel toEntity(Plan plan) {
        return PlanTravel.builder()
                .name(name)
                .address(address)
                .longitude(longitude)
                .latitude(latitude)
                .image(image)
                .plan(plan)
                .build();
    }
}
