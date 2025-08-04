package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    // 특정 게시글과 사용자에 대한 좋아요가 존재하는지 확인
    Optional<Like> findByBoardAndUserId(Board board, Long userId);
}
