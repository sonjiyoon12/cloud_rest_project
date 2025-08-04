package com.cloud.cloud_rest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> saveBoard(@RequestBody BoardRequestDto.SaveDto saveDto,
                                       @RequestParam Long userId) {
        Board savedBoard = boardService.saveBoard(saveDto, userId);
        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

    // 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                         @RequestBody BoardRequestDto.UpdateDto updateDto,
                                         @RequestParam Long userId) {
        Board updateBoard = boardService.updateBoard(boardId, updateDto, userId);
        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
                                         @RequestParam Long userId) {
        boardService.deleteBoard(boardId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
