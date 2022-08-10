package com.app.vple.domain.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

@Getter
public class UserUpdateDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$", message = "닉네임은 3글자 이상 10글자 이하, 특수 문자를 허용하지 않습니다.")
    private String nickname;

    @URL
    private String image;
}
