package com.app.vple.domain;

import com.app.vple.domain.enums.TourType;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

/**
 * 사용자의 위치 기반으로 주변 관광지이 보여지는 기능
 * 따라서 사용자 위치(경도, 위도) 약 500미터 내에 있는 가게들을 보여질 수 있는 로직 필요
 */
@Entity
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
    private TourType tourType;

    @Column(nullable = false)
    @Formula(value = "(select sum(rating) from reviews where id=recommand_tour_spot_id)")
    private float rating;

    @Column(nullable = false)
    private String profile;

    private String image;
}
