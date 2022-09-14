package com.app.vple.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddMateDto {

    @ApiModelProperty(example = "127.0614134613")
    private Double longitude;

    @ApiModelProperty(example = "37.08970789081")
    private Double latitude;
}
