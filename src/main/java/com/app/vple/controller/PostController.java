package com.app.vple.controller;


import com.app.vple.domain.dto.*;
import com.app.vple.service.PostService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PostController {

    private final HttpSession httpSession;

    private final PostService postService;

    private final int PAGE_SIZE = 20;

    @GetMapping("/api/post")
    public ResponseEntity<?> postList(
            @PageableDefault(size = PAGE_SIZE, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        try {
            Page<PostListDto> posts = postService.findPost(pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/post/category")
    public ResponseEntity<?> postCategoryList(
            @PageableDefault(size = PAGE_SIZE, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "category") String category) {

        try {
            boolean flag = category.equals("review"); // review, none
            Page<PostListDto> postByCategory = postService.findPostByCategory(flag, pageable);
            return new ResponseEntity<>(postByCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/post/hashtag")
    public ResponseEntity<?> postHashtagList(
            @PageableDefault(size = PAGE_SIZE) Pageable pageable,
            @RequestParam(name = "keyword") String hashtag) {
        try {
            Page<PostListDto> hashtagPost = postService.findHashtagPost(hashtag, pageable);
            return new ResponseEntity<>(hashtagPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/post/{id}")
    public ResponseEntity<?> postDetails(@PathVariable Long id) {
        try {
            PostDetailDto post = postService.findPostDetails(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/post/details/{id}")
    public ResponseEntity<?> postDetailModify(@PathVariable Long id) {
        try {
            PostDetailUpdateDto postDetail = postService.findPostDetail(id);
            return new ResponseEntity<>(postDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/post/search")
    public ResponseEntity<?> postSearch(
            @PageableDefault(size = PAGE_SIZE, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "keyword") String keyword) {
        try {
            Page<PostListDto> posts = postService.searchPost(keyword, pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error post add", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/post")
    public ResponseEntity<?> postAdd(@Validated @RequestBody PostUploadDto postUploadDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String email = loginUser.getEmail();
            String title = postService.addPost(postUploadDto, email);
            return new ResponseEntity<>(title + " 게시글 등록이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/auth/post/{id}")
    public ResponseEntity<?> postRemove(@PathVariable Long id) {
        try {
            String title = postService.removePost(id);
            return new ResponseEntity<>(title + "을 삭제했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/auth/post/{id}")
    public ResponseEntity<?> postModify(@PathVariable Long id,
                                        @Validated @RequestBody PostUpdateDto postUpdateDto) {
        try {
            String title = postService.modifyPost(id, postUpdateDto);
            return new ResponseEntity<>(title + " 게시글 수정에 성공하였습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/auth/post/like/{id}")
    public ResponseEntity<?> postLikeAdd(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String result = postService.changePostLike(id, loginUser.getEmail());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
