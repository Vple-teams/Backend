package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="recommand_tour_spot")
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
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "tourSpot")
    private List<TourSpotReview> reviews;

    @Column(name = "review_count")
    @Formula(value = "(select count(*) from tourspot_reviews where tourspot_reviews.tourspot_id = recommand_tour_spot_id)")
    private Integer reviewCount;
}
