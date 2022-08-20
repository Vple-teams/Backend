package com.app.vple.domain.dto;

import com.app.vple.domain.enums.MessageType;
import lombok.Data;

@Data
public class ChattingMessageDto {

    private MessageType type;

    private String sessionId;

    private String message;

    private String sender; // nickname
}
