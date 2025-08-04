package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.board.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardrepository;

    @Transactional
    public LikeResponseDto toggleLike(LikeRequestDto requestDto) {
        // 1. 게시글 존재 여부 확인
        Board board = boardrepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        // 2. 사용자가 이미 좋아요를 눌렀는지 확인
        Optional<Like> existingLike = likeRepository.findByBoardAndUserId(board, requestDto.getUserId());

        if (existingLike.isPresent()) {
            // 3.1 이미 좋아요를 눌렀다면, 좋아요 취소
            likeRepository.delete(existingLike.get());
            board.setLikeCount(board.getLikeCount() - 1); // 좋아요 취소
            return new LikeResponseDto(false, board.getLikeCount());
        } else {
            // 3.2 좋아요를 누르지 않았다면 --> 좋아요 추가
            Like newLike = new Like();
            newLike.setBoard(board);
            newLike.setUserId(requestDto.getUserId());
            newLike.setLikedAt(LocalDateTime.now());
            likeRepository.save(newLike);
            board.setLikeCount(board.getLikeCount() + 1); // 게시글 좋아요 증가
            return new LikeResponseDto(true, board.getLikeCount());
        }
    }
}
