package com.cloud.cloud_rest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody BoardRequestDto.SaveDto saveDto,
                                       @RequestParam Long userId) {
        try {
            Board savedBoard = boardService.saveBoard(saveDto, userId);
            return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 목록 조회 (페이징, 정렬, 검색 포함)
    @GetMapping
    public ResponseEntity<Page<Board>> getBoardList(
            // 기본 값 최신순
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        Page<Board> boardPage = boardService.getBoardList(pageable, search);
        return new ResponseEntity<>(boardPage, HttpStatus.OK);
    }


    // 특정 게시글 조회 (조회수 증가 포함)
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        try {
            // 조회수 증가 로직을 별도로 호출
            boardService.increaseViews(boardId);
            // 게시글 조회 로직 호출
            Board board = boardService.getBoard(boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                         @RequestBody BoardRequestDto.UpdateDto updateDto,
                                         @RequestParam Long userId) {
        try {
            Board updatedBoard = boardService.updateBoard(boardId, updateDto, userId);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
                                         @RequestParam Long userId) {
        try {
            boardService.deleteBoard(boardId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
