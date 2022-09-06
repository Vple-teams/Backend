package com.app.vple.domain.dto;

import com.app.vple.domain.ChattingMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChattingMessageOnlyDto {

    private String message;

    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String date;

    public ChattingMessageOnlyDto(ChattingMessage entity) {
        this.nickname = entity.getSender().getNickname();
        this.message = entity.getMessage();
        this.date = entity.getCreatedDate().toString();
    }
}
