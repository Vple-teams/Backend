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

@Entity     // 테이블과 매핑할 클래스이므로 해당 어노테이션 명시
@Getter     // Getter 메소드를 생성해 준다. (롬복을 사용해서 이용 가능)
@Builder
@Table(name = "ploggings")  // 엔티티와 매핑할 테이블을 "ploggings"로 지정
@AllArgsConstructor
public class Plogging extends BaseTime {

    @Id     // id 변수에 기본키 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 데이터베이스에 기본키 생성 위임, sql사용하므로 IDENTITY속성 이용
    @Column(name = "plogging_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)      // N:1관계,
    @JoinColumn(name = "user_id")       // user 변수를 "user_id"로 외래키 매핑 (생략 가능)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ploggingWriter;      // 글쓴이

    @Column(nullable = false)
    private String nickname;       // 아이디

    @Column(nullable = false)
    private String title;       //제목

    @Column(nullable = false)
    private String html;     // 내용

    @Column(nullable = false)
    private Integer views = 0;      // 조회수

    @Column(nullable = false)
    @Formula(value = "select count(*) from plogging_comments where plogging_comments.plogging_id = plogging_id")
    private Integer ploggingCommentCount;       //댓글 수

    @Column(nullable = false)
    private Integer nowPeople; // 현재 참여 인원

    @Column(nullable = false)
    private Integer totalPeople;    // 목표 참여 인원

    @Column(nullable = false)
    private LocalDate startDate;        // 모집 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate;      // 모집 마감 날짜

    @Column(nullable = false)
    private LocalDate executionDate;    // 실행 날짜

    @Column(nullable = false)
    private String district; // 서울시, 경기도 등 대분류

    @Column(nullable = false)
    private String city; // 구, city

    @OneToOne(fetch = FetchType.LAZY)       // 필요로 할 때 데이터를 가져온다.
    @JoinColumn(name = "plogging_team_id")      //외래키
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PloggingTeam team;      // 랭킹 시스템을 도입하면 사용하려했던 팀제도

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

