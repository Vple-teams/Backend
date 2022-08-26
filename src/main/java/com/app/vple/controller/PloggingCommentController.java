package com.app.vple.controller;

import com.app.vple.domain.dto.*;
import com.app.vple.service.PloggingCommentService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/plogging_comment")
public class PloggingCommentController {
    private final PloggingCommentService ploggingCommentService;

    private final HttpSession httpSession;

    @GetMapping
    public ResponseEntity<?> PloggingCommentList() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            List<MyPloggingCommentDto> ploggingComments = ploggingCommentService.findPloggingComment(loginUser.getEmail());
            return new ResponseEntity<>(ploggingComments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> ploggingCommentAdd(@Validated @RequestBody PloggingCommentUploadDto ploggingComment) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String ploggingComment1 = ploggingCommentService.addPloggingComment(ploggingComment, loginUser.getEmail());
            return new ResponseEntity<>(ploggingComment1 + " 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> ploggingCommentRemove(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String ploggingComment = ploggingCommentService.removePloggingComment(id, loginUser.getEmail());
            return new ResponseEntity<>(ploggingComment+ " 삭제 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> ploggingCommentModify(@PathVariable Long id,
                                           @Validated @RequestBody PloggingCommentUpdateDto ploggingCommentUpdateDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String ploggingComment = ploggingCommentService.modifyPloggingComment(id, ploggingCommentUpdateDto, loginUser.getEmail());
            return new ResponseEntity<>(ploggingComment + " 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
