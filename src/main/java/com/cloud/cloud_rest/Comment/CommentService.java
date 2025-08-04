package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글 등록 메서드
     * @param requestDto 댓글 내용, 게시글 ID, 비밀 댓글 여부를 담은 DTO
     * @param userId 댓글 작성자의 ID
     * @return 등록된 댓글 정보를 담은 응답 DTO
     */
    @Transactional
    public CommentResponseDto writeComment(CommentRequestDto requestDto, Long userId) {
        // 1. 게시글 존재 여부 확인
        Board board = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 2. 댓글 엔티티 생성 및 저장
        Comment newComment = Comment.builder()
                .board(board)
                .userId(userId)
                .content(requestDto.getContent())
                .isSecret(requestDto.getIsSecret() != null ? requestDto.getIsSecret() : false)
                .build();
        commentRepository.save(newComment);
        return new CommentResponseDto(newComment);
    }

    /**
     * 댓글 수정 메서드
     * @param commentId 수정할 댓글의 ID
     * @param requestDto 새로운 내용을 담은 DTO
     * @param userId 댓글 수정 권한 확인을 위한 사용자 ID
     */
    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto, Long userId) {
        // 1. 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 2. 수정 권한 확인
        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        // 3. 댓글 내용 수정
        comment.setContent(requestDto.getContent());
    }

    /**
     * 댓글 삭제 메서드
     * @param commentId 삭제할 댓글의 ID
     * @param userId 댓글 삭제 권한 확인을 위한 사용자 ID
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        // 1. 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 2. 삭제 권한 확인
        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }

        // 3. 댓글 삭제
        commentRepository.delete(comment);
    }
}
