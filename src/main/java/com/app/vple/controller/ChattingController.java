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

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/chat")
public class ChattingController {

    private final ChattingService chattingService;

    private final HttpSession httpSession;

    @PostMapping
    public ResponseEntity<?> createChattingRoom(@RequestParam String another) {

        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String sessionId = chattingService.createChattingRoom(another, loginUser.getEmail());
            return new ResponseEntity<>("채팅방 생성 완료 [sessionId]: " + sessionId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllChattingRoom() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            List<ChattingRoomDto> chattingRooms = chattingService.findAllChattingRoom(loginUser.getEmail());
            return new ResponseEntity<>(chattingRooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findChattingRoomDetail(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            ChattingRoomDetailDto chattingRoomDetail = chattingService.findChattingRoomDetail(id, loginUser.getName());
            return new ResponseEntity<>(chattingRoomDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChattingRoom(@PathVariable Long id) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            chattingService.deleteWebsocketSession(loginUser.getName(), id);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
