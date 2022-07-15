package com.app.vple.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PloggingState {

    RECRUIT("모집중"), CANCELLED("취소됨"), FINISHED("모집 완료");

    private final String value;
}