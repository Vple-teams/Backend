package com.app.vple.repository;

import com.app.vple.domain.PloggingComment;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PloggingCommentRepository extends JpaRepository<PloggingComment, Long> {

    Optional<PloggingComment> findPloggingCommentByPloggingCommentWriterAndId(User user, Long id);

    List<PloggingComment> findPloggingCommentByPloggingCommentWriter(User user);
}
