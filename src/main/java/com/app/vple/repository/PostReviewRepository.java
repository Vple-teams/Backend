package com.app.vple.repository;

import com.app.vple.domain.PostReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReviewRepository extends JpaRepository<PostReview, Long> {

    List<PostReview> findAllByHashTag(String hashTagName);
}
