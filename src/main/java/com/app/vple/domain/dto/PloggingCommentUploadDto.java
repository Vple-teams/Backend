package com.app.vple.domain.dto;

import com.app.vple.domain.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PloggingCommentUploadDto {
    @NotBlank(message = "내용이 필요합니다.")
    @Size(min = 2, max = 200, message = "댓글 내용은 최소 2자부터 200자까지 가능합니다.")
    private String content;

    private Long ploggingId;

    public PloggingComment toEntity(User user, Plogging plogging) {
        return PloggingComment.builder()
                .content(content)
                .plogging(plogging)
                .nickname(user.getNickname())
                .ploggingCommentWriter(user)
                .build();
    }
}
