package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    FEMALE("여자"), MALE("남자");

    private final String value;

    public static Gender toGender(String gender) {
        if(gender.equals("female") || gender.equals("F"))
            return Gender.FEMALE;

        else if(gender.equals("male") || gender.equals("M"))
            return Gender.MALE;

        return Gender.MALE;
    }
}
