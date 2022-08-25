package com.app.vple.repository;

import com.app.vple.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIsReviewPost(boolean isReviewPost);

    List<Post> findAll();

    List<Post> findByTitleContaining(String title);

    @Query("SELECT p from PostReview pr, Post p where p.isReviewPost = true and pr.hashTag like %:hashtag%")
    List<Post> findAllByHashTag(String hashtag);
}
