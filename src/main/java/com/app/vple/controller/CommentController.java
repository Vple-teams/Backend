package com.app.vple.controller;


import com.app.vple.domain.dto.CommentUpdateDto;
import com.app.vple.domain.dto.CommentUploadDto;
import com.app.vple.domain.dto.MyCommentsDto;
import com.app.vple.service.CommentService;
import com.app.vple.service.model.SessionLoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/comment")
public class CommentController {

    private final CommentService commentService;

    private final HttpSession httpSession;

    @ApiOperation(value = "유저가 달은 모든 댓글 보기")
    @GetMapping
    public ResponseEntity<?> commentList() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            List<MyCommentsDto> comments = commentService.findComment(loginUser.getEmail());
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "게시글에 댓글달기")
    @PostMapping
    public ResponseEntity<?> commentAdd(@Validated @RequestBody CommentUploadDto comment) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String comment1 = commentService.addComment(comment, loginUser.getEmail());
            return new ResponseEntity<>(comment1 + " 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "댓글 삭제하기")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> commentRemove(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String comment = commentService.removeComment(id, loginUser.getEmail());
            return new ResponseEntity<>(comment+ " 삭제 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "댓글 수정하기")
    @PatchMapping("/{id}")
    public ResponseEntity<?> commentModify(@PathVariable Long id,
                                           @Validated @RequestBody CommentUpdateDto commentUpdateDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String comment = commentService.modifyComment(id, commentUpdateDto, loginUser.getEmail());
            return new ResponseEntity<>(comment + " 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
