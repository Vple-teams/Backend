package com.app.vple.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity     // JPA가 관리하는 클래스, JPA를 사용해 테이블과 매핑할 클래스는 반드시 @Entity를 붙여야 한다.
@Table(name = "comments")
@Getter
@Builder
@AllArgsConstructor
public class Comment extends BaseTime {

    public Comment() {}     // 엔티티클래스는 파라미터 없는 public 또는 protected 생성자 필요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User commentWriter;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public void setUpdateContent(String content) {
        this.content = content;
    }
}
