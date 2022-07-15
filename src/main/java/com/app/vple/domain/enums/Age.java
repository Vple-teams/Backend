package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Age {

    TEEN("10~19"), TWENTIES("20~29"), THIRTIES("30~39"), FORTIES("40~49"), FIFTIES("50~59"), SIXTIES("60~69"), SENIOR("70~");

    private final String value;

    public static Age toAge(String age) {
        if(age.equals("10~19") || age.equals("10-19"))
            return TEEN;
        else if(age.equals("20~29") || age.equals("20-29"))
            return TWENTIES;
        else if(age.equals("30~39") || age.equals("30-39"))
            return THIRTIES;
        else if(age.equals("40~49") || age.equals("40-49"))
            return FORTIES;
        else if(age.equals("50~59") || age.equals("50-59"))
            return FIFTIES;
        else if(age.equals("60~69") || age.equals("60-69"))
            return SIXTIES;
        else
            return SENIOR;
    }
}
