package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.CommentLike.CommentLike;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.board.BoardRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloud.cloud_rest.CommentLike.CommentLikeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final BoardLikeRepository likeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeResponseDto toggleLike(LikeRequestDto requestDto) {
        if ((requestDto.getBoardId() == null && requestDto.getCommentId() == null) ||
                (requestDto.getBoardId() != null && requestDto.getCommentId() != null)) {
            throw new IllegalArgumentException("게시글 ID 또는 댓글 ID 중 하나만 필수입니다.");
        }

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("ID " + requestDto.getUserId() + "를 가진 사용자를 찾을 수 없습니다."));

        if (requestDto.getBoardId() != null) {
            return toggleBoardLike(requestDto.getBoardId(), user);
        } else {
            return toggleCommentLike(requestDto.getCommentId(), user);
        }
    }

    private LikeResponseDto toggleBoardLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("ID " + boardId + "를 가진 게시글을 찾을 수 없습니다."));

        Optional<BoardLike> existingLike = likeRepository.findByBoardAndUser(board, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            board.setLikeCount(board.getLikeCount() - 1);
            return LikeResponseDto.builder()
                    .isLiked(false)
                    .newLikeCount(board.getLikeCount())
                    .build();
        } else {
            boolean isOwner = board.getUser() != null && board.getUser().getUserId().equals(user.getUserId());

            BoardLike newLike = BoardLike.builder()
                    .board(board)
                    .user(user)
                    .isOwner(isOwner)
                    .build();
            likeRepository.save(newLike);
            board.setLikeCount(board.getLikeCount() + 1);
            return LikeResponseDto.builder()
                    .isLiked(true)
                    .newLikeCount(board.getLikeCount())
                    .build();
        }
    }

    private LikeResponseDto toggleCommentLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("ID " + commentId + "를 가진 댓글을 찾을 수 없습니다."));

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndUser(comment, user);

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.setLikeCount(comment.getLikeCount() - 1);
            return LikeResponseDto.builder()
                    .isLiked(false)
                    .newLikeCount(comment.getLikeCount())
                    .build();
        } else {
            CommentLike newLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(newLike);
            comment.setLikeCount(comment.getLikeCount() + 1);
            return LikeResponseDto.builder()
                    .isLiked(true)
                    .newLikeCount(comment.getLikeCount())
                    .build();
        }
    }
}