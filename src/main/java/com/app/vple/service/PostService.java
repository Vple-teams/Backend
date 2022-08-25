package com.app.vple.service;

import com.app.vple.domain.CheckDuplicatedPostLike;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.PostDetailDto;
import com.app.vple.domain.dto.PostListDto;
import com.app.vple.domain.dto.PostUpdateDto;
import com.app.vple.domain.dto.PostUploadDto;
import com.app.vple.repository.CheckDuplicatedPostLikeRepository;
import com.app.vple.repository.PostRepository;
import com.app.vple.repository.PostReviewRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CheckDuplicatedPostLikeRepository checkDuplicatedPostLikeRepository;

    public Page<PostListDto> findPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostListDto::new);
    }

    public List<PostListDto> findPostByCategory(boolean category) {
        // 0: none, 1: review type
        List<Post> postByCategory = postRepository.findAllByIsReviewPost(category);

        if(postByCategory.isEmpty()) {
            String type = category ? "후기" : "일반";
            throw new ArrayIndexOutOfBoundsException(type + "에 속하는 게시글이 존재하지 않습니다.");
        }

        return postByCategory.stream().map(PostListDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PostDetailDto findPostDetails(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        if (!post.isReviewPost()) {
            post.addViews();
            postRepository.save(post);
            return new PostDetailDto(post);
        }
        else {
            throw new IllegalStateException("해당 게시글이 존재하지 않습니다.");
        }
    }

    @Transactional
    public String addPost(PostUploadDto uploadPost, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
            postRepository.save(uploadPost.toEntity(user));
            return uploadPost.getTitle();
        } catch (Exception e) {
            throw new IllegalStateException("형식이 잘못되었습니다.");
        }
    }

    @Transactional
    public String removePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
        return post.getTitle();
    }

    @Transactional
    public String modifyPost(Long id, PostUpdateDto updateDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        post.updatePost(updateDto);
        postRepository.save(post);

        return post.getTitle();
    }

    @Transactional
    public String changePostLike(Long id, String email) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

        CheckDuplicatedPostLike postLike = checkDuplicatedPostLikeRepository.findByUserAndPost(user, post)
                .orElse(null);

        if (postLike == null) {
            checkDuplicatedPostLikeRepository.save(new CheckDuplicatedPostLike(user, post));
            return post.getTitle() + " 좋아요 + 1";
        }
        else {
            checkDuplicatedPostLikeRepository.delete(postLike);
            return post.getTitle() + " 좋아요 취소";
        }
    }

    public List<PostListDto> searchPost(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        if (posts.isEmpty()) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다.");
        }

        return posts.stream().map(PostListDto::new).collect(Collectors.toList());
    }

    public List<PostListDto> findHashtagPost(String hashtag) {
        // hashtag: 여행, 식당, 관광지, 플로깅, 펀딩
        List<Post> hashtagPost = postRepository.findAllByHashTag(hashtag);

        return hashtagPost.stream().map(
                PostListDto::new
        ).collect(Collectors.toList());
    }
}
