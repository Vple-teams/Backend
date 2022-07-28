package com.app.vple.controller;

import com.app.vple.domain.dto.UserDetailDto;
import com.app.vple.service.UserOAuth2Service;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            UserDetailDto user = userService.findUser(loginUser.getEmail());

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}