package com.app.vple.repository;

import com.app.vple.domain.User;
import com.app.vple.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    UserFollow getByFromAndTo(User from, User to);
}
