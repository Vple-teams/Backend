package com.app.vple.domain.dto;

import com.app.vple.domain.ChattingRoom;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChattingRoomDetailDto {

    private Long id;

    private String userANickname;

    private String userBNickname;

    private List<ChattingMessageOnlyDto> messages;

    public ChattingRoomDetailDto(ChattingRoom entity) {
        this.id = entity.getId();
        this.userANickname = entity.getUserA().getNickname();
        this.userBNickname = entity.getUserB().getNickname();
        this.messages = entity.getMessages().stream().map(
                ChattingMessageOnlyDto::new
        ).collect(Collectors.toList());
    }
}
