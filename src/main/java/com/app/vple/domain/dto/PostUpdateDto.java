package com.app.vple.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostUpdateDto {

    @NotBlank(message = "제목이 필요합니다.")
    @Size(min = 2, max = 20, message = "제목의 길이는 최소 2부터 최대 20까지입니다.")
    private String title;

    @NotNull(message = "본문이 비어있습니다.")
    @Size(min = 2)
    private String html;

    @NotNull(message = "게시글의 카테고리가 필요합니다.")
    private boolean isReviewPost;

    private List<String> hashTags;
}
