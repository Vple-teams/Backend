package com.app.vple.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;

import com.app.vple.domain.enums.TourType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자의 위치 기반으로 주변 관광지이 보여지는 기능
 * 따라서 사용자 위치(경도, 위도) 약 500미터 내에 있는 가게들을 보여질 수 있는 로직 필요
 */
@Entity(name="recommand_tour_spot")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommandTourSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommand_tour_spot_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    @Formula("(select sum(r.rating) from tour_reviews r where r.tour_id = recommand_tour_spot_id)")
    private float rating;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private TourType tourType;
}
