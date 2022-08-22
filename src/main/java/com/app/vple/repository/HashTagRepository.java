package com.app.vple.repository;

import com.app.vple.domain.HashTag;
import com.app.vple.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    List<HashTag> findAllByHashTag(String hashTagName);

    @Modifying
    Long deleteAllByPost(Post post);
}
