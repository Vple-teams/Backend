package com.app.vple.controller;

import com.app.vple.service.UserFollowService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/follow")
public class UserFollowController {

    private final UserFollowService userFollowService;

    private final HttpSession httpSession;

    @PostMapping("/{toUserId}")
    public ResponseEntity<?> follow(@PathVariable Long toUserId) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String nickname = userFollowService.followUser(toUserId, loginUser.getEmail());
            return new ResponseEntity<>(nickname + " 팔로우 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<?> unfollow(@PathVariable Long toUserId) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String nickname = userFollowService.unFollowUser(toUserId, loginUser.getEmail());
            return new ResponseEntity<>(nickname + " 언팔로우 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
