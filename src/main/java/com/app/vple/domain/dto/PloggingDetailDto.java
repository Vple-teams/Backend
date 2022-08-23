package com.app.vple.domain.dto;

import com.app.vple.domain.BaseTime;
import com.app.vple.domain.Plogging;
import com.app.vple.domain.PloggingComment;
import com.app.vple.domain.PloggingTeam;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PloggingDetailDto extends BaseTime {

    private String nickname;

    private String title;

    private String html;

    private int views;

    private Integer ploggingCommentCount;

    private int nowPeople;

    private int totalPeople;

    private LocalDateTime createDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate executionDate;

    private String district;

    private String city;

    //private PloggingTeam team;

    private List<PloggingCommentDto> ploggingComments;

    public PloggingDetailDto(Plogging entity){
        this.nickname = entity.getNickname();
        this.title = entity.getTitle();
        this.html = entity.getHtml();
        this.views = entity.getViews();
        this.nowPeople = entity.getNowPeople();
        this.totalPeople = entity.getTotalPeople();
        this.createDate = entity.getCreatedDate();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.executionDate = entity.getExecutionDate();
        this.district = entity.getDistrict();
        this.city = entity.getCity();
        //this.team =
        this.ploggingComments = entity.getPloggingComments().stream().map(
                PloggingCommentDto::new
        ).collect(Collectors.toList());
    }
}
