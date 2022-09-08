package com.app.vple.service;

import com.app.vple.domain.*;
import com.app.vple.domain.dto.*;
import com.app.vple.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final HashTagRepository hashTagRepository;

    private final CheckDuplicatedPostLikeRepository checkDuplicatedPostLikeRepository;

    private final RecommandTourSpotRepository recommandTourSpotRepository;

    private final RecommandRestaurantRepository recommandRestaurantRepository;

    public Page<PostListDto> findPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostListDto::new);
    }

    public Page<PostListDto> findPostByCategory(boolean category, Pageable pageable) {
        Page<Post> postByCategory = postRepository.findAllByIsReviewPost(category, pageable);

        if(postByCategory.isEmpty()) {
            String type = category ? "후기" : "일반";
            throw new ArrayIndexOutOfBoundsException(type + "에 속하는 게시글이 존재하지 않습니다.");
        }

        return postByCategory.map(PostListDto::new);
    }

    @Transactional
    public PostDetailDto findPostDetails(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        post.addViews();
        postRepository.save(post);
        return new PostDetailDto(post);
    }

    @Transactional
    public String addPost(PostUploadDto uploadPost, String email, String nickname) {

        User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

        RecommandTourSpot tourSpot = null;
        RecommandRestaurant restaurant = null;
        if (uploadPost.getRestaurantId() != null) {
            restaurant = recommandRestaurantRepository.findById(uploadPost.getRestaurantId()).orElse(null);
        }
        else {
            tourSpot = recommandTourSpotRepository.findById(uploadPost.getTourspotId()).orElse(null);
        }

        postRepository.save(uploadPost.toEntity(user, restaurant, tourSpot));
        Post recentPost = postRepository.findFirstByNicknameOrderByCreatedDateDesc(nickname).get(0);

        for (String name : uploadPost.getHashtag()) {
                hashTagRepository.save(new HashTag(recentPost, name));
        }

        return uploadPost.getTitle();
    }

    @Transactional
    public String removePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
        return post.getTitle();
    }

    public PostDetailUpdateDto findPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 없습니다.")
        );

        return new PostDetailUpdateDto(post);
    }

    @Transactional
    public String modifyPost(Long id, PostUpdateDto updateDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        post.updatePost(updateDto);

        if (post.isReviewPost()) {
            hashTagRepository.deleteAllByPost(post);
            for (String tag : updateDto.getHashTags()) {
                hashTagRepository.save(new HashTag(post, tag));
            }
        }

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

    public Page<PostListDto> searchPost(String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContaining(keyword, pageable);
        if (posts.isEmpty()) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다.");
        }

        return posts.map(PostListDto::new);
    }

    public Page<PostListDto> findHashtagPost(String hashtag, Pageable pageable) {
        Page<Post> hashtagPost = postRepository.findAllByHashTag(hashtag, pageable);

        return hashtagPost.map(PostListDto::new);
    }
}
