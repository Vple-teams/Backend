package com.app.vple.service;

import com.app.vple.domain.Comment;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.CommentUpdateDto;
import com.app.vple.domain.dto.CommentUploadDto;
import com.app.vple.domain.dto.MyCommentsDto;
import com.app.vple.repository.CommentRepository;
import com.app.vple.repository.PostRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public List<MyCommentsDto> findComment(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );
        List<Comment> commentByCommentWriter = commentRepository.findCommentByCommentWriter(user);

        if(commentByCommentWriter.isEmpty())
            throw new ArrayIndexOutOfBoundsException("작성한 댓글이 없습니다.");

        return commentByCommentWriter.stream().map(
                MyCommentsDto::new
        ).collect(Collectors.toList());
    }

    @Transactional
    public String addComment(CommentUploadDto comment, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        Long postId = comment.getPostId();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );

        commentRepository.save(comment.toEntity(user, post));

        if(comment.getContent().length() > 10)
            return comment.getContent().substring(0, 10) + "...";
        else
            return comment.getContent();
    }

    @Transactional
    public String removeComment(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NoSuchElementException("해당 댓글이 없습니다.")
        );

        if (comment.getCommentWriter().getEmail().equals(email))
            commentRepository.delete(comment);
        else
            throw new IllegalStateException("해당 댓글의 작성자가 아닙니다.");

        if(comment.getContent().length() > 10)
            return comment.getContent().substring(0, 10) + "...";
        else
            return comment.getContent();
    }

    @Transactional
    public String modifyComment(Long id, CommentUpdateDto commentDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(
                () -> new NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findCommentByCommentWriterAndId(user, id).orElseThrow(
                () -> new NoSuchElementException("해당 댓글이 없습니다.")
        );

        if (comment.getCommentWriter().getEmail().equals(email)) {
            comment.setUpdateContent(commentDto.getContent());
            commentRepository.save(comment);
        }
        else
            throw new IllegalStateException("해당 댓글의 작성자가 아닙니다.");

        if(comment.getContent().length() > 10)
            return comment.getContent().substring(0, 10) + "...";
        else
            return comment.getContent();
    }
}
