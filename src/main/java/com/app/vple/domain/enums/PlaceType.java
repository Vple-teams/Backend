package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceType {

    RESTAURANT("식당"), CAFE("카페"), TOUR("관광지"), MUSEUM("박물관"), LEISURE("레저");

    private final String value;
}
