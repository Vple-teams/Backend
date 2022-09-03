package com.app.vple.domain.dto;

import com.app.vple.domain.*;
import lombok.Data;

@Data
public class PloggingCommentUpdateDto {

    private String content;

    private Long ploggingId;

    public PloggingComment toEntity(User user, Plogging plogging) {
        return PloggingComment.builder()
                .ploggingCommentWriter(user)
                .nickname(user.getNickname())
                .plogging(plogging)
                .content(content)
                .build();
    }
}
