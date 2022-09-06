package com.app.vple.domain.dto;

import com.app.vple.domain.ChattingRoom;
import lombok.Data;

@Data
public class ChattingRoomDto {

    private Long id;

    private String anotherUser; // 상대방의 username

    public ChattingRoomDto(ChattingRoom entity, String myNickname) {
        this.id = entity.getId();

        if (myNickname.equals(entity.getUserA().getNickname())) {
            this.anotherUser = entity.getUserB().getNickname();
        }
        else {
            this.anotherUser = entity.getUserA().getNickname();
        }
    }
}
