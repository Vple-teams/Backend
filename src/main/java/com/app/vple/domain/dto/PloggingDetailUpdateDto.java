package com.app.vple.domain.dto;

import com.app.vple.domain.HashTag;
import com.app.vple.domain.Plogging;
import com.app.vple.domain.Post;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 수정시 기존에 작성했던 내용 불러오기
 */
@Data
public class PloggingDetailUpdateDto {

    private String title;

    private String html;

    private LocalDate startData;

    private LocalDate endData;

    private LocalDate executionData;

    private String district;

    private String city;

    public PloggingDetailUpdateDto(Plogging entity) {
        this.title = entity.getTitle();
        this.html = entity.getHtml();
        this.startData = entity.getStartDate();
        this.endData = entity.getEndDate();
        this.executionData = entity.getExecutionDate();
        this.district = entity.getDistrict();
        this.city = entity.getCity();

    }
}
