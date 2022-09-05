package com.app.vple.domain.dto;

import com.app.vple.domain.UserFollow;
import lombok.Data;

@Data
public class Following {

    private Long id;

    private String nickname;

    private String introduction;

    private String image;

    public Following(UserFollow user) {
        this.id = user.getTo().getId();
        this.nickname = user.getTo().getNickname();
        this.introduction = user.getTo().getIntroduction();
        this.image = user.getTo().getImage();
    }
}
