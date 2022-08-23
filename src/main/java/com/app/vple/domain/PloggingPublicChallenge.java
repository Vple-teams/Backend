package com.app.vple.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "plogging_public_challenge")
@Getter
@Builder
@AllArgsConstructor
public class PloggingPublicChallenge extends BaseTime {

    @Id     // id 변수에 기본키 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 데이터베이스에 기본키 생성 위임, sql사용하므로 IDENTITY속성 이용
    @Column(name = "plogging_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;        // 모집 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate;      // 모집 마감 날짜

    @Column(nullable = false)
    private LocalDate executionDate;    // 실행 날짜

    @Column(nullable = false)
    private String address;     // 플로깅 장소

    @Column(nullable = false)
    private String link;     // 하이퍼링크
}
