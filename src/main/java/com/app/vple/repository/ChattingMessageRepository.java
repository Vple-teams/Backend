package com.app.vple.repository;

import com.app.vple.domain.ChattingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, Long> {
}
