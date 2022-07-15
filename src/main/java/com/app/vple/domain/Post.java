package com.app.vple.domain;

import com.app.vple.domain.dto.PostUpdateDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@DynamicUpdate @DynamicInsert
@Builder
@AllArgsConstructor
public class Post extends BaseTime {

    public Post() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String html;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User postWriter;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer likesCount;

    @Column(nullable = false)
    @Formula(value = "(select count(*) from comments where comments.post_id = post_id)")
    private Integer commentCount;

    @Column(nullable = false, name = "is_review_post")
    private boolean isReviewPost; // 0: none, 1: review post

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Column(nullable = false)
    private Integer views;

    @OneToMany(mappedBy = "post")
    @Where(clause = "is_review_post = 1")
    private List<PostReview> postReview;

    public void updatePost(PostUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.html = updateDto.getHtml();
        this.isReviewPost = updateDto.isReviewPost();
    }
}
