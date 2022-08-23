package com.app.vple.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class UserProfileDto {

    @URL
    private String url;

    @Email
    private String email;

}
