package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {

    private Long id;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    public ReviewDto(Post review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.date = review.getCreatedDate();
    }

}
