package com.app.vple.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long id;

    @Column(nullable = false)
    private String introduce;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private float longitude;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiredDate;
}
