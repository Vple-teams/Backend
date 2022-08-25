package com.app.vple.controller;


import com.app.vple.domain.dto.PostDetailDto;
import com.app.vple.domain.dto.PostListDto;
import com.app.vple.domain.dto.PostUpdateDto;
import com.app.vple.domain.dto.PostUploadDto;
import com.app.vple.service.PostService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final HttpSession httpSession;

    private final PostService postService;

    @GetMapping("/api/post")
    public ResponseEntity<?> postList(
            @PageableDefault(size = 8, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        try {
            Page<PostListDto> posts = postService.findPost(pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/post/category")
    public ResponseEntity<?> postCategoryList(@RequestParam(name = "category") String category) {

        try {
            boolean flag = category.equals("review"); // review, none
            List<PostListDto> postByCategory = postService.findPostByCategory(flag);
            return new ResponseEntity<>(postByCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/post/hashtag")
    public ResponseEntity<?> postHashtagList(@RequestParam(name = "keyword") String hashtag) {
        try {
            List<PostListDto> hashtagPost = postService.findHashtagPost(hashtag);
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

    @GetMapping("/auth/post/search")
    public ResponseEntity<?> postSearch(@RequestParam(name = "keyword") String keyword) {
        try {
            List<PostListDto> posts = postService.searchPost(keyword);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
