package com.app.vple.controller;

import com.app.vple.domain.dto.*;
import com.app.vple.service.PloggingService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/plogging")
public class PloggingController {

    private final HttpSession httpSession;

    private final PloggingService ploggingService;

    @GetMapping
    public ResponseEntity<?> ploggingList(
            @PageableDefault(size = 8, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        try {
            Page<PloggingListDto> ploggings = ploggingService.findPlogging(pageable);
            return new ResponseEntity<>(ploggings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ploggingDetails(@PathVariable Long id) {
        try {
            PloggingDetailDto plogging = ploggingService.findPloggingDetails(id);
            return new ResponseEntity<>(plogging, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> ploggingDetailModify(@PathVariable Long id) {
        try {
            PloggingDetailUpdateDto ploggingDetail = ploggingService.findPloggingDetail(id);
            return new ResponseEntity<>(ploggingDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> ploggingSearch(
            @PageableDefault(size = 8, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "keyword") String keyword) {
        try {
            Page<PloggingListDto> ploggings = ploggingService.searchPlogging(keyword, pageable);
            return new ResponseEntity<>(ploggings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> ploggingAdd(@Validated @RequestBody PloggingUploadDto ploggingUploadDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String email = loginUser.getEmail();
            String title = ploggingService.addPlogging(ploggingUploadDto, email);
            return new ResponseEntity<>(title + " 게시글 등록이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> ploggingRemove(@PathVariable Long id) {
        try {
            String title = ploggingService.removePlogging(id);
            return new ResponseEntity<>(title + " 을 삭제했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> ploggingModify(@PathVariable Long id,
                                            @Validated @RequestBody PloggingUpdateDto ploggingUpdateDto) {
        try {
            String title = ploggingService.modifyPlogging(id, ploggingUpdateDto);
            return new ResponseEntity<>(title + " 게시글 수정에 성공하였습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}
