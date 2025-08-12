package com.cloud.cloud_rest.board.board_tag;

import com.cloud.cloud_rest.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    void deleteByBoard(Board board);
}