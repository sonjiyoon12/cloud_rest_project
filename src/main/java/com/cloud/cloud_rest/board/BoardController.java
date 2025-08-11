package com.cloud.cloud_rest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 목록 조회 (GET /api/boards)
    @GetMapping
    public ResponseEntity<List<BoardResponseDto.ListDto>> getBoardList( // 1. 리턴 타입을 List로 변경
                                                                        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                        @ModelAttribute BoardRequestDto.SearchDTO searchDTO) {

        Page<BoardResponseDto.ListDto> responseDtoPage = boardService.getBoardList(pageable, searchDTO);
        List<BoardResponseDto.ListDto> dtoList = responseDtoPage.getContent(); // 2. getContent()로 내용물(List)만 꺼내기

        return ResponseEntity.ok(dtoList); // 3. 순수한 List를 리턴
    }

    // 특정 게시글 상세 조회 (GET /api/boards/{boardId})
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto.DetailDto> getBoard(@PathVariable Long boardId) {
        try {
            BoardResponseDto.DetailDto responseDto = boardService.getBoardWithIncreaseViews(boardId);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // 게시글 생성 (POST /api/boards)
    @PostMapping
    public ResponseEntity<BoardResponseDto.DetailDto> saveBoard(
            @RequestBody BoardRequestDto.SaveDto saveDto) throws IOException {
        try {
            BoardResponseDto.DetailDto savedBoard = boardService.saveBoard(saveDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    // 게시글 수정 (PUT /api/boards/{boardId})
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto.UpdateDto> updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto.UpdateDto updateDto) {
        if (updateDto.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId가 누락되었습니다.");
        }
        try {
            BoardResponseDto.UpdateDto updatedBoard = boardService.updateBoard(boardId, updateDto);
            return ResponseEntity.ok(updatedBoard);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    // 게시글 삭제 (DELETE /api/boards/{boardId})
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId) {
        try {
            boardService.deleteBoard(boardId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // 특정 사용자가 댓글을 작성한 모든 게시글 조회 (GET "/users/{userId}/commented-boards")
    @GetMapping("/users/{userId}/commented-boards")
    public ResponseEntity<Page<BoardResponseDto.ListDto>> getBoardsCommentedByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<Board> boardPage = boardService.getBoardsCommentedByUser(userId, pageable);
            Page<BoardResponseDto.ListDto> responseDtoPage = boardPage.map(BoardResponseDto.ListDto::new);
            return ResponseEntity.ok(responseDtoPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}