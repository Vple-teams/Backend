package com.app.vple.repository;

import com.app.vple.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByisReviewPost(boolean isReviewPost);
}
