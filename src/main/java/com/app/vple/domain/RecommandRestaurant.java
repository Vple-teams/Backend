package com.app.vple.domain;

import com.app.vple.domain.enums.VeganType;
import lombok.Getter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="recommand_restaurant")
@Getter
public class RecommandRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommand_restaurant_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private String introduction;

    private String phoneNumber;

    private String openTime;

    private Float rating;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    private String image;

    @OneToMany(mappedBy = "recommandRestaurant")
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantReview> reviews;

    @Column(name = "review_count")
    @Formula(value = "(select count(*) from restaurant_reviews where restaurant_reviews.restaurant_id = recommand_restaurant_id)")
    private Integer reviewCount;
}
