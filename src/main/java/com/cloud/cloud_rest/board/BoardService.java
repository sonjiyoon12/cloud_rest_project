package com.cloud.cloud_rest.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public Board saveBoard(BoardRequestDto.SaveDto saveDto, Long userId) {
        Board board = Board.builder()
                .title(saveDto.getTitle())
                .content(saveDto.getContent())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .views(0)
                .build();
        return boardRepository.save(board);
    }

    // 특정 Id의 게시물 조회 + 조회수 증가 포함
    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
        board.setViews(board.getViews() + 1);
        return boardRepository.save(board);
    }

    // 게시물 수정
    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto.UpdateDto updateDto, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 수정 권한이 없습니다.");
        }

        board.update(updateDto);
        return boardRepository.save(board);
    }

    // 게시물 삭제
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

    // Board 엔티티를 위한 Repository (인터페이스만)
    interface BoardRepository extends JpaRepository<Board, Long> {
    }
}
