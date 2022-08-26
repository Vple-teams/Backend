package com.app.vple.service;

import com.app.vple.domain.*;
import com.app.vple.domain.dto.*;
import com.app.vple.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingCommentService {

    private final PloggingCommentRepository ploggingCommentRepository;

    private final UserRepository userRepository;

    private final PloggingRepository ploggingRepository;

    public List<PloggingMyCommentDto> findPloggingComment(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );
        List<PloggingComment> ploggingCommentByPloggingCommentWriter = ploggingCommentRepository.findPloggingCommentByPloggingCommentWriter(user);

        if(ploggingCommentByPloggingCommentWriter.isEmpty())
            throw new ArrayIndexOutOfBoundsException("작성한 댓글이 없습니다.");

        return ploggingCommentByPloggingCommentWriter.stream().map(
                PloggingMyCommentDto::new
        ).collect(Collectors.toList());
    }

    @Transactional
    public String addPloggingComment(PloggingCommentUploadDto ploggingComment, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        Long ploggingId = ploggingComment.getPloggingId();
        Plogging plogging = ploggingRepository.findById(ploggingId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        ploggingCommentRepository.save(ploggingComment.toEntity(user, plogging));

        if(ploggingComment.getContent().length() > 10)
            return ploggingComment.getContent().substring(0, 10) + "...";
        else
            return ploggingComment.getContent();
    }

    @Transactional
    public String removePloggingComment(Long ploggingCommentId, String email) {
        PloggingComment ploggingComment = ploggingCommentRepository.findById(ploggingCommentId).orElseThrow(
                () -> new NoSuchElementException("해당 댓글이 없습니다.")
        );

        if (ploggingComment.getPloggingCommentWriter().getEmail().equals(email))
            ploggingCommentRepository.delete(ploggingComment);
        else
            throw new IllegalStateException("해당 댓글의 작성자가 아닙니다.");

        if(ploggingComment.getContent().length() > 10)
            return ploggingComment.getContent().substring(0, 10) + "...";
        else
            return ploggingComment.getContent();
    }

    @Transactional
    public String modifyPloggingComment(Long id, PloggingCommentUpdateDto ploggingCommentDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        Plogging plogging = ploggingRepository.findById(ploggingCommentDto.getPloggingId()).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        PloggingComment ploggingComment = ploggingCommentRepository.findPloggingCommentByPloggingCommentWriterAndId(user, id).orElseThrow(
                () -> new NoSuchElementException("해당 댓글이 없습니다.")
        );

        if (ploggingComment.getPloggingCommentWriter().getEmail().equals(email)) {
            ploggingComment.setUpdateContent(ploggingCommentDto.getContent());
            ploggingCommentRepository.save(ploggingComment);
        }
        else
            throw new IllegalStateException("해당 댓글의 작성자가 아닙니다.");

        if(ploggingComment.getContent().length() > 10)
            return ploggingComment.getContent().substring(0, 10) + "...";
        else
            return ploggingComment.getContent();
    }
}
