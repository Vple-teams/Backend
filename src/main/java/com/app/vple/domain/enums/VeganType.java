package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum VeganType {

    VEGAN("비건"), LACTO("락토"), OVO("오보"), POLLO("폴로"), PESCO("페스코"), LACTOOVO("락토오보"), ADJUST("조정가능");

    private final String value;


}
