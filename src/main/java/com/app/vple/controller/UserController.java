package com.app.vple.controller;

import com.app.vple.domain.dto.NicknameUpdateDto;
import com.app.vple.domain.dto.UserDetailDto;
import com.app.vple.domain.dto.UserIntroductionDto;
import com.app.vple.domain.dto.UserUpdateDto;
import com.app.vple.service.UserOAuth2Service;
import com.app.vple.service.model.SessionLoginUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final UserOAuth2Service userService;

    private final HttpSession httpSession;

    @ApiOperation(value = "내 정보보기")
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

    @ApiOperation(value = "다른 사람의 프로필 보기")
    @GetMapping("/{id}")
    public ResponseEntity<?> otherUserDetails(@PathVariable Long id) {
        try {
            UserDetailDto user = userService.findUser(id);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "유저 정보 수정하기")
    @PatchMapping
    public ResponseEntity<?> userModify(@RequestBody UserUpdateDto userUpdateDto) {

        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            userService.modifyUser(loginUser.getId(), userUpdateDto);
            return new ResponseEntity<>(loginUser.getEmail() + "님의 정보가 수정되었습니다.", HttpStatus.OK);
        } catch (Exception e) { // 400 error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "유저 닉네임 변경하기")
    @PatchMapping("/nickname")
    public ResponseEntity<?> userNicknameModify(@Validated @RequestBody NicknameUpdateDto nicknameUpdateDto, BindingResult bindingResult) {
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
            userService.modifyUserNickname(loginUser.getId(), nicknameUpdateDto);
            return new ResponseEntity<>("Your nickname has been updated.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "자기소개 추가하고 수정하기")
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

    @ApiOperation(value = "사용자의 가능한 외국어 우선순위 변경 - 외국어 서비스에 적용하려고 만듦")
    @PatchMapping("/language")
    public ResponseEntity<?> userLanguagePriorityModify(@ApiParam(value = "1순위 언어 하나(KOR, ENG, CHN, JPN)", example = "KOR") @RequestBody Map<String, String> language) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            userService.userLanguagePriority(loginUser.getId(), language.get("language"));
            return new ResponseEntity<>("priority change success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("priority change failed", HttpStatus.BAD_REQUEST);
        }
    }
}