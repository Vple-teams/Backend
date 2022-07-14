package com.app.vple.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class PloggingReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_reivew_comment_id")
    private  Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User commentWriter;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_reivew_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PloggingReview ploggingReview;

    public void setUpdateContent(String content) {
        this.content = content;
    }
}
