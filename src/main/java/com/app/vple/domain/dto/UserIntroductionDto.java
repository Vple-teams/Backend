package com.app.vple.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserIntroductionDto {

    @ApiModelProperty(example = "자기소개 한줄 수정 예시")
    private String introduction;
}
