package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tourspot_reviewtags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourSpotReivewTag {
    @Id
    @Column(name = "reviewtags_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String reviewTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TourSpotReview tourSpotReview;

    public TourSpotReivewTag(TourSpotReview tourSpotReview, String name) {
        this.tourSpotReview = tourSpotReview;
        this.reviewTag = name;
    }
}
