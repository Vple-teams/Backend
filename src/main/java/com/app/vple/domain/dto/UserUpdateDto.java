package com.app.vple.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

@Data
public class UserUpdateDto {

    @ApiModelProperty(value = "성별 F / M", example = "F")
    private String gender;

    @ApiModelProperty(value = "나이대 20-29, 30-39, 40-49", example = "20-29")
    private String age;

    @ApiModelProperty(value = "채식 정도 VEGAN, LACTO OVO, POLLO, PESCO, LACTOOVO, ADJUST", example = "VEGAN")
    private String vegetarian;

    @ApiModelProperty(value = "할 줄 아는 언어 0순위가 메인 언어 KOR, ENG, JPN, CHN", example = "[\"KOR\", \"ENG\"]")
    private List<String> languages;

}
