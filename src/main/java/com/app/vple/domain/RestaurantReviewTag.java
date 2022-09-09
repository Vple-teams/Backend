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
@Table(name = "restaurant_reviewtags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantReviewTag {

    @Id
    @Column(name = "reviewtags_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String reviewTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RestaurantReview restaurantReview;

    public RestaurantReviewTag(RestaurantReview restaurantReview, String name) {
        this.restaurantReview = restaurantReview;
        this.reviewTag = name;
    }
}
