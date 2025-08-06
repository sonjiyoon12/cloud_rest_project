package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    // 게시물과 사용자로 좋아요를 찾습니다.
    Optional<BoardLike> findByBoardAndUser(Board board, User user);
}
