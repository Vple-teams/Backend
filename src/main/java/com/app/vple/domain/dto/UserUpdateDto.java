package com.app.vple.domain.dto;

import lombok.Data;


import java.util.List;

@Data
public class UserUpdateDto {

    private String gender;

    private String age;

    private String vegetarian;

    private List<String> languages;

}
