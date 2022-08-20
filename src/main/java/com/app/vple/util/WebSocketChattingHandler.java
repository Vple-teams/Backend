package com.app.vple.util;

import com.app.vple.domain.dto.ChattingMessageDto;
import com.app.vple.service.ChattingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketChattingHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final ChattingService chattingService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);
        ChattingMessageDto chatMessage = objectMapper.readValue(payload, ChattingMessageDto.class);
        chattingService.handleMessage(session, chatMessage);
    }

}
