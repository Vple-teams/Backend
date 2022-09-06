package com.app.vple.repository;

import com.app.vple.domain.ChattingRoom;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {

    List<ChattingRoom> findAllByUserAOrUserB(User me, User me1);

    boolean existsByUserAOrUserB(User userA, User userB);
}
