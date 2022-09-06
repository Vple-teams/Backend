package com.app.vple.domain.dto;

import lombok.Data;

@Data
public class SendMessageDto {

    private Long roomId;

    private Long senderId;

    private String message;

    private String messageType;

}
