package com.app.vple.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "check_duplicated_plan_likes")
public class CheckDuplicatedPlanLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public CheckDuplicatedPlanLike(User user, Plan plan) {
        this.user = user;
        this.plan = plan;
    }
}
