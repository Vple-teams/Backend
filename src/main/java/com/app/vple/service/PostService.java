package com.app.vple.service;

import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.PostDetailDto;
import com.app.vple.domain.dto.PostListDto;
import com.app.vple.domain.dto.PostUpdateDto;
import com.app.vple.domain.dto.PostUploadDto;
import com.app.vple.repository.PostRepository;
import com.app.vple.repository.PostReviewRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    private final PostReviewRepository postReviewRepository;

    private final UserRepository userRepository;

    public List<PostListDto> findPost() {
        List<Post> posts = postRepository.findAll();

        if(posts.isEmpty())
            throw new ArrayIndexOutOfBoundsException("게시글이 없습니다.");

        return posts.stream()
                .map(PostListDto::new)
                .collect(Collectors.toList());
    }

    public List<PostListDto> findPostByCategory(boolean category) {
        // 0: none, 1: review type
        List<Post> postByCategory = postRepository.findAllByisReviewPost(category);

        if(postByCategory.isEmpty()) {
            String type = category ? "후기" : "일반";
            throw new ArrayIndexOutOfBoundsException(type + "에 속하는 게시글이 존재하지 않습니다.");
        }

        return postByCategory.stream().map(
                PostListDto::new
        ).collect(Collectors.toList());
    }

    public PostDetailDto findPostDetails(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        if (!post.isReviewPost())
            return new PostDetailDto(post);

        else
            throw new IllegalStateException("해당 게시글이 존재하지 않습니다.");
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
}