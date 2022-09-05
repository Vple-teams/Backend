package com.app.vple.domain.dto;

import com.app.vple.domain.UserFollow;
import lombok.Data;

@Data
public class Follower {

    private Long id;

    private String nickname;

    private String introduction;

    private String image;

    public Follower(UserFollow user) {
        this.id = user.getFrom().getId();
        this.nickname = user.getFrom().getNickname();
        this.introduction = user.getFrom().getIntroduction();
        this.image = user.getFrom().getImage();
    }
}
