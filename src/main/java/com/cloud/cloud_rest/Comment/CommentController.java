package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto) {
        Long userId = 1L;
        CommentResponseDto responseDto = commentService.writeComment(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 특정 게시글의 댓글 목록을 페이징하여 조회
    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<Page<Comment>> getCommentsByBoardId(
            @PathVariable Long boardId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> commentPage = boardService.getCommentsByBoardId(boardId, pageable);
        return new ResponseEntity<>(commentPage, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequestDto requestDto) {
        Long userId = 1L;
        commentService.updateComment(commentId, requestDto, userId);
        return ResponseEntity.ok("댓글이 성공적으로 수정 되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제 되었습니다.");
    }
}
