package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.board.BoardRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void saveComment(Long boardId, CommentRequestDto.SaveDto saveDto, SessionUser sessionUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + boardId));

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        Comment comment = Comment.builder()
                .board(board)
                .user(user)
                .content(saveDto.getContent())
                .isSecret(saveDto.isSecret())
                .build();

        commentRepository.save(comment);
    }

    public void updateComment(Long commentId, CommentRequestDto.UpdateDto updateDto, SessionUser sessionUser) {
        Comment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        comment.update(updateDto);
    }

    public void deleteComment(Long commentId, SessionUser sessionUser) {
        Comment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        commentRepository.delete(comment);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));
    }

    private void checkOwnership(Comment comment, SessionUser sessionUser) {
        if (!Objects.equals(comment.getUser().getUserId(), sessionUser.getId())) {
            throw new SecurityException("해당 댓글에 대한 권한이 없습니다.");
        }
    }
}