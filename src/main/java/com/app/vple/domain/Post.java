package com.app.vple.domain;

import com.app.vple.domain.dto.PostUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
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

    @Column(name = "likes_count")
    @Formula(value = "(select count(*) from check_duplicated_post_likes where check_duplicated_post_likes.post_id = post_id)")
    private Integer likesCount;

    @Column(nullable = false)
    @Formula(value = "(select count(*) from comments where comments.post_id = post_id)")
    private Integer commentCount;

    @Column(nullable = false, name = "is_review_post")
    private boolean isReviewPost; // 0: none, 1: review post

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<HashTag> hashTags;

    private Integer views;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @Where(clause = "is_review_post = 1")
    private Place place;

    public void updatePost(PostUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.html = updateDto.getHtml();
        this.isReviewPost = updateDto.isReviewPost();
    }

    @PrePersist
    public void prePersist() {
        this.likesCount = this.likesCount == null ? 0 : this.likesCount;
        this.views = this.views == null ? 0 : this.views;
    }

    public void addViews() {
        this.views += 1;
    }
}
