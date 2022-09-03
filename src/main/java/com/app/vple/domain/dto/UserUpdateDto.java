package com.app.vple.domain.dto;

import com.app.vple.domain.User;
import com.app.vple.domain.enums.VeganType;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

@Data
public class UserUpdateDto {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{3,10}$", message = "닉네임은 3글자 이상 10글자 이하, 특수 문자를 허용하지 않습니다.")
    private String nickname;

    @URL
    private String image;

    private String gender;

    private String age;

    private String vegetarian;

}
