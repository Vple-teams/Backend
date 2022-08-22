package com.app.vple.service;

import com.app.vple.domain.ChattingMessage;
import com.app.vple.domain.ChattingRoom;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.ChattingMessageDto;
import com.app.vple.domain.dto.ChattingRoomDetailDto;
import com.app.vple.domain.dto.ChattingRoomDto;
import com.app.vple.domain.enums.MessageType;
import com.app.vple.repository.ChattingMessageRepository;
import com.app.vple.repository.ChattingRoomRepository;
import com.app.vple.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingService {

    private final ObjectMapper objectMapper;

    private final ChattingRoomRepository chattingRoomRepository;

    private final ChattingMessageRepository chattingMessageRepository;

    private final UserRepository userRepository;

    @Transactional
    public String createChattingRoom(String anotherNickname, String email) {
        User another = userRepository.findByNickname(anotherNickname)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 유저가 존재하지 않습니다.")
                );

        User me = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 유저가 존재하지 않습니다.")
                );

        List<ChattingRoom> chattingRooms1 = chattingRoomRepository.findAllByUserANicknameOrUserBNickname(anotherNickname, me.getNickname());
        List<ChattingRoom> chattingRooms2 = chattingRoomRepository.findAllByUserANicknameOrUserBNickname(me.getNickname(), anotherNickname);

        if (!chattingRooms1.isEmpty() || !chattingRooms2.isEmpty()) {
            throw new IllegalStateException("이미 채팅방이 생성되어 있습니다.");
        }

        String sessionId = UUID.randomUUID().toString();
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .sessionId(sessionId)
                .userA(another)
                .userANickname(anotherNickname)
                .userB(me)
                .userBNickname(me.getNickname())
                .build();
        chattingRoomRepository.save(chattingRoom);
        return sessionId;
    }

    @Transactional
    public  void handleMessage(WebSocketSession session, ChattingMessageDto chatMessage) throws IOException {
        if (chatMessage.getType().equals(MessageType.JOIN)) {
            User me = userRepository.findByNickname(chatMessage.getSender()).orElseThrow(
                    () -> new NoSuchElementException("해당 유저가 존재하지 않습니다.")
            );
            chatMessage.setMessage(me.getNickname() + "님이 입장했습니다.");
        }
        sendMessage(session, chatMessage);
    }

    @Transactional
    public void sendMessage(WebSocketSession session, ChattingMessageDto chattingMessageDto) throws IOException {
        session.sendMessage(new TextMessage(
                objectMapper.writeValueAsString(chattingMessageDto.getMessage())));

        ChattingRoom chattingRoom = chattingRoomRepository.getChattingRoomBySessionId(chattingMessageDto.getSessionId());

        User me = userRepository.findByNickname(chattingMessageDto.getSender()).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 존재하지 않습니다.")
        );

        ChattingMessage chattingMessage = ChattingMessage.builder()
                .message(chattingMessageDto.getMessage())
                .room(chattingRoom)
                .sender(me)
                .senderName(me.getNickname())
                .type(chattingMessageDto.getType())
                .build();

        chattingMessageRepository.save(chattingMessage);
    }

    @Transactional
    public void deleteWebsocketSession(String nickname, Long id) {
        ChattingRoom chattingRoom = chattingRoomRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
        );

        if (chattingRoom.getUserANickname().equals(nickname) || chattingRoom.getUserBNickname().equals(nickname)) {
            chattingRoomRepository.delete(chattingRoom);
        }
        else {
            throw new IllegalStateException("해당 채팅방의 참여자가 아닙니다.");
        }
    }

    public List<ChattingRoomDto> findAllChattingRoom(String email) {
        User me = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 유저가 존재하지 않습니다.")
                );
        String myNickname = me.getNickname();

        List<ChattingRoom> chattingRooms = chattingRoomRepository.findAllByUserANicknameOrUserBNickname(myNickname, myNickname);
        return chattingRooms.stream().map(
                (entity) -> new ChattingRoomDto(entity, myNickname)
        ).collect(Collectors.toList());
    }

    public ChattingRoomDetailDto findChattingRoomDetail(Long id, String nickname) {
        ChattingRoom chattingRoom = chattingRoomRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
                );

        if (chattingRoom.getUserANickname().equals(nickname) || chattingRoom.getUserBNickname().equals(nickname)) {
            return new ChattingRoomDetailDto(chattingRoom);
        }
        else {
            throw new IllegalStateException("해당 채팅방에 접근할 수 없습니다. (채팅방에 속해있지 않습니다.)");
        }
    }
}
