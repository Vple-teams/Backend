package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "plogging_comments")
@Getter
@Builder
@AllArgsConstructor
public class PloggingComment extends BaseTime {

    public PloggingComment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_comment_id")
    private  Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ploggingCommentWriter;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plogging_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Plogging plogging;

    public void setUpdateContent(String content) {
        this.content = content;
    }
}
