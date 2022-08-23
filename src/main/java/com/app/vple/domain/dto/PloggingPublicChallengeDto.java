package com.app.vple.domain.dto;

import com.app.vple.domain.PloggingPublicChallenge;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PloggingPublicChallengeDto {

    private Long id;

    private String title;

    private String content;

    private LocalDate startDate;        // 모집 시작 날짜

    private LocalDate endDate;      // 모집 마감 날짜

    private LocalDate executionDate;    // 실행 날짜

    private String address;     // 플로깅 장소

    private String link;     // 하이퍼링크

    public PloggingPublicChallengeDto(PloggingPublicChallenge entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.executionDate = entity.getExecutionDate();
        this.address = entity.getAddress();
        this.link = entity.getLink();
    }
}
