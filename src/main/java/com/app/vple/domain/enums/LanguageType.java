package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LanguageType {
    KOR("한국어"), ENG("영어"), JP("일본어"), CHN("중국어");

    private final String value;

}
