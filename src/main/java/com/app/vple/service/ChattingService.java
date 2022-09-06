package com.app.vple.service;

import com.app.vple.domain.ChattingMessage;
import com.app.vple.domain.ChattingRoom;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.ChattingMessageDto;
import com.app.vple.domain.dto.ChattingRoomDetailDto;
import com.app.vple.domain.dto.ChattingRoomDto;
import com.app.vple.domain.dto.SendMessageDto;
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

    private final ChattingRoomRepository chattingRoomRepository;

    private final ChattingMessageRepository chattingMessageRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createChattingRoom(Long myUserId, Long anotherUserId) {
        User me = userRepository.getById(myUserId);
        User another = userRepository.findById(anotherUserId)
                .orElseThrow(
                        () -> new NoSuchElementException("유저가 존재하지 않습니다.")
                );

        boolean chatRoom1 = chattingRoomRepository.existsByUserAOrUserB(me, another);
        boolean chatRoom2 = chattingRoomRepository.existsByUserAOrUserB(another, me);

        if (chatRoom1 || chatRoom2) {
            throw new IllegalStateException("이미 채팅방이 생성되어 있습니다.");
        }

        ChattingRoom newRoom = ChattingRoom.builder()
                .userA(another)
                .userB(me)
                .build();

        chattingRoomRepository.save(newRoom);
    }

    @Transactional
    public void saveMessage(SendMessageDto message) {
        ChattingRoom chattingRoom = chattingRoomRepository.getById(message.getRoomId());

        User me = userRepository.getById(message.getSenderId());

        ChattingMessage chattingMessage = ChattingMessage.builder()
                .room(chattingRoom)
                .sender(me)
                .message(message.getMessage())
                .type(MessageType.valueOf(message.getMessageType()))
                .build();

        chattingMessageRepository.save(chattingMessage);
    }

    @Transactional
    public void deleteChattingRoom(Long id, Long userId) {
        ChattingRoom chattingRoom = chattingRoomRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
        );
        User me = userRepository.getById(userId);

        chattingRoom.setChattingRoomDelete(me);
        chattingRoomRepository.save(chattingRoom);
    }

    public List<ChattingRoomDto> findAllChattingRoom(Long id) {
        User me = userRepository.getById(id);

        List<ChattingRoom> chattingRooms = chattingRoomRepository.findAllByUserAOrUserB(me, me);
        return chattingRooms.stream().map(
                (entity) -> new ChattingRoomDto(entity, me.getNickname())
        ).collect(Collectors.toList());
    }

    public ChattingRoomDetailDto findChattingRoomDetail(Long id, Long userId) {
        ChattingRoom chattingRoom = chattingRoomRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
                );

        User me = userRepository.getById(userId);
        if (chattingRoom.getUserA() == me || chattingRoom.getUserB() == me) {
            return new ChattingRoomDetailDto(chattingRoom);
        }
        else {
            throw new IllegalStateException("해당 채팅방의 참여자가 아닙니다.");
        }
    }
}
