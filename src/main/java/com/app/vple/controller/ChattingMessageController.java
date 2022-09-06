package com.app.vple.controller;

import com.app.vple.domain.dto.SendMessageDto;
import com.app.vple.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingMessageController {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChattingService chattingService;

    @MessageMapping("/chat") // pub/chat
    public void message(SendMessageDto message) {
        log.info("chat message: {}", chattingService);

        if ("JOIN".equals(message.getMessageType())) {
            message.setMessage("상대방이 입장했습니다.");
        }
        else if ("LEAVE".equals(message.getMessageType())) {
            message.setMessage("상대방이 퇴장했습니다.");
            chattingService.deleteChattingRoom(message.getRoomId(), message.getSenderId());
        }
        chattingService.saveMessage(message);
        messagingTemplate.convertAndSend("/sub/chat/" + message.getRoomId(), message);
    }

}
