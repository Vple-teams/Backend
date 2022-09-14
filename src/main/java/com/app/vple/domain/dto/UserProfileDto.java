package com.app.vple.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class UserProfileDto {

    @ApiModelProperty(example = "https://image-test.com/test-example.jpg")
    @URL
    private String url;

    @Email
    @ApiModelProperty(example = "my-email@address.com")
    private String email;

}
