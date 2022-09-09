package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tourspot_reviews")
public class TourSpotReview extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tourspot_review_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User reviewer;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "tourSpotReview")
    private List<TourSpotReivewTag> reivewTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourspot_id")
    private RecommandTourSpot tourSpot;
}
