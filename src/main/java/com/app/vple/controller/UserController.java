package com.app.vple.controller;

import com.app.vple.domain.dto.UserDetailDto;
import com.app.vple.domain.dto.UserIntroductionDto;
import com.app.vple.domain.dto.UserUpdateDto;
import com.app.vple.service.UserOAuth2Service;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final UserOAuth2Service userService;

    private final HttpSession httpSession;

    @GetMapping
    public ResponseEntity<?> userDetails() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            UserDetailDto user = userService.findUser(loginUser.getId());

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> otherUserDetails(@PathVariable Long id) {
        try {
            UserDetailDto user = userService.findUser(id);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping
    public ResponseEntity<?> userModify(@Validated @RequestBody UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                String message = objectError.getDefaultMessage();

                sb.append(message);
            });

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            userService.modifyUser(loginUser.getEmail(), userUpdateDto);
            return new ResponseEntity<>(loginUser.getEmail() + "님의 정보가 수정되었습니다.", HttpStatus.OK);
        } catch (Exception e) { // 400 error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> userIntroductionAddAndModify(@Validated @RequestBody UserIntroductionDto userIntroductionDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            userService.modifyAndSaveUserIntroduction(loginUser.getEmail(), userIntroductionDto.getIntroduction());

            return new ResponseEntity<>("자기소개 추가/수정 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("자기소개 추가/수정 실패", HttpStatus.BAD_REQUEST);
        }
    }
}