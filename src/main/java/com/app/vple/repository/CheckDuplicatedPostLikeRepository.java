package com.app.vple.repository;

import com.app.vple.domain.CheckDuplicatedPostLike;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckDuplicatedPostLikeRepository extends JpaRepository<CheckDuplicatedPostLike, Long> {

    Optional<CheckDuplicatedPostLike> findByUserAndPost(User user, Post post);
}
