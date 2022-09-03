package com.app.vple.domain;

import com.app.vple.domain.enums.VeganType;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommand_restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RecommandRestaurant recommandRestaurant;

    @Column(nullable = false)
    private String name;

    private String image;

    private String veganType;

}
