package com.app.vple.domain.dto;

import com.app.vple.domain.ChattingMessage;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChattingMessageOnlyDto {

    private String message;

    private String nickname;

    private String date;

    public ChattingMessageOnlyDto(ChattingMessage entity) {
        this.nickname = entity.getSenderName();
        this.message = entity.getMessage();
        this.date = entity.getCreatedDate().toString();
    }
}
