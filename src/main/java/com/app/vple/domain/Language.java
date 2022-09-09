package com.app.vple.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "languages")
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long id;

    @Column(name = "language_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer priority; // 우선순위가 0인 언어로 먼저 보여짐

    public Language(User user, String language, Integer pr) {
        this.user = user;
        this.name = language;
        this.priority = pr;
    }

    public void changePR(Integer pr) {
        this.priority = pr;
    }
}
