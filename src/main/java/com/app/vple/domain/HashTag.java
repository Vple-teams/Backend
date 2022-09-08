package com.app.vple.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "hashtags")
@ToString
@NoArgsConstructor
public class HashTag {

    @Id
    @Column(name = "post_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String hashTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public HashTag(Post post, String hashTag) {
        this.post = post;
        this.hashTag = hashTag;
        this.createdDate = post.getCreatedDate();
    }
}
