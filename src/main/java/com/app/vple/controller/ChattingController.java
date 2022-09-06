package com.app.vple.controller;

import com.app.vple.domain.dto.ChattingRoomDetailDto;
import com.app.vple.domain.dto.ChattingRoomDto;
import com.app.vple.service.ChattingService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/chat/room")
public class ChattingController {

    private final ChattingService chattingService;

    private final HttpSession httpSession;

    @GetMapping
    public ResponseEntity<?> findAllChattingRoom() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            List<ChattingRoomDto> chattingRooms = chattingService.findAllChattingRoom(loginUser.getId());
            return new ResponseEntity<>(chattingRooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findChattingRoomDetail(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            ChattingRoomDetailDto chattingRoomDetail = chattingService.findChattingRoomDetail(id, loginUser.getId());
            return new ResponseEntity<>(chattingRoomDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createChattingRoom(@RequestBody Map<String, Long> anotherId) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            chattingService.createChattingRoom(loginUser.getId(), anotherId.get("anotherId"));
            return new ResponseEntity<>("created chatting room", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
