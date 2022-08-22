package com.app.vple.repository;

import com.app.vple.domain.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {

    List<ChattingRoom> findAllByUserANicknameOrUserBNickname(String userANickname, String userBNickname);

    ChattingRoom getChattingRoomBySessionId(String sessionId);

}
