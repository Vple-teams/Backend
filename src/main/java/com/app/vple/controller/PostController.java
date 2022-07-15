package com.app.vple.controller;


import com.app.vple.domain.dto.PostDetailDto;
import com.app.vple.domain.dto.PostListDto;
import com.app.vple.domain.dto.PostUpdateDto;
import com.app.vple.domain.dto.PostUploadDto;
import com.app.vple.service.PostService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final HttpSession httpSession;

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> postList() {
        try {
            List<PostListDto> posts = postService.findPost();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> postDetails(@PathVariable Long id) {
        try {
            PostDetailDto post = postService.findPostDetails(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> postRemove(@PathVariable Long id) {
        try {
            String title = postService.removePost(id);
            return new ResponseEntity<>(title + "을 삭제했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> postModify(@PathVariable Long id,
                                        @Validated @RequestBody PostUpdateDto postUpdateDto) {
        try {
            String title = postService.modifyPost(id, postUpdateDto);
            return new ResponseEntity<>(title + " 게시글 수정에 성공하였습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
