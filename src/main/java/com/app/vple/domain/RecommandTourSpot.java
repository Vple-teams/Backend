package com.app.vple.domain;

import com.app.vple.domain.enums.TourType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name="recommand_tour_spot")
@Getter
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
    private String profile;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private TourType tourType;
}
