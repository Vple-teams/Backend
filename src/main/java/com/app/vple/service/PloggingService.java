package com.app.vple.service;

import com.app.vple.domain.Plogging;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.*;
import com.app.vple.repository.PloggingRepository;
import com.app.vple.repository.UserRepository;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingService {

    private final PloggingRepository ploggingRepository;

    private final UserRepository userRepository;

    public Page<PloggingListDto> findPlogging(Pageable pageable){
        Page<Plogging> ploggings = ploggingRepository.findAll(pageable);

        return ploggings.map(PloggingListDto::new);
    }

    @Transactional
    public PloggingDetailDto findPloggingDetails(Long ploggingId){
        Plogging plogging = ploggingRepository.findById(ploggingId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        plogging.addViews();
        ploggingRepository.save(plogging);

        return new PloggingDetailDto(plogging);
    }


    public PloggingDetailUpdateDto findPloggingDetail(Long id) {
        Plogging plogging = ploggingRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 없습니다.")
        );
        return new PloggingDetailUpdateDto(plogging);
    }

    public Page<PloggingListDto> searchPlogging(String keyword, Pageable pageable) {
        Page<Plogging> ploggings = ploggingRepository.findByTitleContaining(keyword, pageable);
        if (ploggings.isEmpty()) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다.");
        }

        return ploggings.map(PloggingListDto::new);
    }

    @Transactional
    public String addPlogging(PloggingUploadDto uploadPlogging, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다.")
            );
            ploggingRepository.save(uploadPlogging.toEntity(user));
            return uploadPlogging.getTitle();
        } catch (Exception e) {
            throw new IllegalStateException("형식이 잘못되었습니다.");
        }
    }

    @Transactional
    public String removePlogging(Long id) {
        Plogging plogging = ploggingRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        ploggingRepository.delete(plogging);
        return plogging.getTitle();
    }

    @Transactional
    public String modifyPlogging(Long id, PloggingUpdateDto updateDto) {
        Plogging plogging = ploggingRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        plogging.updatePlogging(updateDto);

        ploggingRepository.save(plogging);
        return plogging.getTitle();
    }

}
