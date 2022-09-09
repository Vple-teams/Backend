package com.app.vple.repository;

import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findFirstByPostWriterOrderByCreatedDateDesc(User user);

    Page<Post> findAllByIsReviewPost(boolean isReviewPost, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    @Query("SELECT p from HashTag h, Post p where p = h.post and h.hashTag like :hashtag order by p.createdDate desc")
    Page<Post> findAllByHashTag(String hashtag, Pageable pageable);
}
