package com.app.vple.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "ploggings")
public class Plogging extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int views = 0;

    @Column(nullable = false)
    private int nowPeople = 1; // 현재 인원 기본적으로 글쓴이 1명

    @Column(nullable = false)
    private int totalPeople;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private String district; // 서울시, 경기도 등 대분류

    @Column(nullable = false)
    private String city; // 구, city

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PloggingTeam team;

    @OneToMany(mappedBy = "plogging")
    private List<PloggingComment> comments;
}

