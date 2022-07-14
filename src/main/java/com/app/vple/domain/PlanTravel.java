package com.app.vple.domain;

import com.app.vple.domain.enums.TourType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class PlanTravel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_travel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Plan plan;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Enumerated(EnumType.STRING)
    private TourType tourType;

    private String image;
}
