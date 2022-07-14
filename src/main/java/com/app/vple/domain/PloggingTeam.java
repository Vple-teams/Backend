package com.app.vple.domain;

import javax.persistence.*;

@Entity
public class PloggingTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_team_id")
    private  Long id;

    @Column(nullable = false)
    private String name;
}
