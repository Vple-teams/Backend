package com.app.vple.repository;

import com.app.vple.domain.Comment;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByCommentWriterAndId(User user, Long id);

    List<Comment> findCommentByCommentWriter(User user);
}

