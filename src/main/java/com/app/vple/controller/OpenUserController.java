package com.app.vple.controller;

import com.app.vple.domain.dto.UserProfileDto;
import com.app.vple.service.UserOAuth2Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OpenUserController {

    private final UserOAuth2Service userOAuth2Service;

    @ApiOperation(value = "내 프로필 사진 수정하기, image server에서 호출하는 용도")
    @PostMapping ("/api/user/profile")
    public ResponseEntity<?> userModifyImage(@RequestBody UserProfileDto userProfileDto) {
        try {
            userOAuth2Service.modifyUserImage(userProfileDto.getUrl(), userProfileDto.getEmail());
            return new ResponseEntity<>("프로필 사진 변경 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
