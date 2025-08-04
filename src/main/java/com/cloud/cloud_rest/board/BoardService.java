package com.cloud.cloud_rest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board saveBoard(BoardRequestDto.SaveDto saveDto, Long userId) {
        Board board = Board.builder()
                .title(saveDto.getTitle())
                .content(saveDto.getContent())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .views(0)
                .likeCount(0)
                .build();
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable, String search) {
        if (search != null && !search.trim().isEmpty()) {
            return boardRepository.searchByKeyword(search, pageable);
        }
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
    }

    @Transactional
    public void increaseViews(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
        board.setViews(board.getViews() + 1);
    }

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

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

}
