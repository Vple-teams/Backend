package com.app.vple.repository;

import com.app.vple.domain.User;
import com.app.vple.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    UserFollow getByFromAndTo(User from, User to);

    List<UserFollow> findByFrom(User from);

    List<UserFollow> findByTo(User to);

}
