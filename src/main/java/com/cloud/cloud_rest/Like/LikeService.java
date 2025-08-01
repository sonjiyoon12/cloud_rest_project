package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LikeService {

    private final  LikeRepository likeRepository;
    private final  Boardrepository boardrepository;


    public LikeResponseDto toggleLike(LikeRequestDto requestDto) {
        // 1. 게시글 존재 여부 확인
        Board board = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Optional<Like> existingLike = likeRepository.findByBoardAndUserId(board,requestDto.getUserId());
        if (existingLike.isPresent()) {

        }

}
