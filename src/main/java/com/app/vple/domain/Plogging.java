package com.app.vple.domain;

import com.app.vple.domain.dto.PloggingUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "ploggings")
@AllArgsConstructor
public class Plogging extends BaseTime {

    public Plogging() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ploggingWriter;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String html;

    @Column(nullable = false)
    private Integer views = 0;

    @Column(nullable = false)
    @Formula(value = "(select count(*) from plogging_comments where plogging_comments.plogging_comment_id = plogging_id)")
    private Integer ploggingCommentCount;

    @Column(nullable = false)
    private Integer nowPeople;

    @Column(nullable = false)
    private Integer totalPeople;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate executionDate;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PloggingTeam team;

    @OneToMany(mappedBy = "plogging")
    private List<PloggingComment> ploggingComments;

    public void updatePlogging(PloggingUpdateDto updateDto){

        this.title = updateDto.getTitle();
        this.html = updateDto.getHtml();
        this.startDate = updateDto.getStartDate();
        this.endDate = updateDto.getEndDate();
        this.executionDate = updateDto.getExecutionDate();
        this.district = updateDto.getDistrict();
        this.city = updateDto.getCity();
    }

    @PrePersist
    public void prePersist(){
        this.views = this.views == null ? 0 : this.views;
        this.nowPeople = this.nowPeople == null ? 0 : this.nowPeople;
        this.totalPeople = this.totalPeople == null ? 0 : this.totalPeople;
    }

    public void addViews() { this.views += 1; }
}

