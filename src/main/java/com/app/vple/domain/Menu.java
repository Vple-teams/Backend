package com.app.vple.domain;

import com.app.vple.domain.enums.VeganType;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private VeganType veganType;

}
