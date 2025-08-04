package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
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
    private final CommentRepository commentRepository; // CommentRepository 의존성 추가

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


    // 게시글별 댓글 목록을 페이징하여 조회
    @Transactional(readOnly = true)
    public Page<Comment> getCommentsByBoardId(Long boardId, Pageable pageable) {
        return commentRepository.findByBoardBoardId(boardId, pageable);
    }

    // 특정 사용자가 댓글을 작성한 모든 게시글을 조회
    @Transactional(readOnly = true)
    public Page<Board> getBoardsCommentedByUser(Long userId, Pageable pageable) {
        return boardRepository.findBoardsCommentedByUser(userId, pageable);
    }
}
