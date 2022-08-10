package com.app.vple.repository;

import com.app.vple.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByIsReviewPost(boolean isReviewPost, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
