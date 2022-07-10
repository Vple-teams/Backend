package com.app.vple.repository;

import com.app.vple.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByPostId(Long postId);
}

