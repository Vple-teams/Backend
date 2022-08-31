package com.app.vple.domain.dto;

import com.app.vple.domain.Plan;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanListDto {

    private Long id;

    private String title;

    private String nickname;

    private Integer likeCount;

    private LocalDate startDate;

    private LocalDate endDate;

    public PlanListDto(Plan entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickname = entity.getUser().getNickname();
        this.likeCount = entity.getLikesCount();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
    }
}
