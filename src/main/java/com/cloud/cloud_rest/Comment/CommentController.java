package com.cloud.cloud_rest.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록 (POST /api/boards/{boardId}/comments)
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto requestDto) {
        Long userId = 1L;
        CommentResponseDto responseDto = commentService.writeComment(boardId, requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 특정 게시글의 댓글 목록을 페이징하여 조회 (GET /api/boards/{boardId}/comments)
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByBoardId(
            @PathVariable Long boardId,
            @PageableDefault(sort = "commentedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponseDto> commentPage = commentService.getCommentsByBoardId(boardId, pageable);
        return new ResponseEntity<>(commentPage, HttpStatus.OK);
    }

    // 댓글 수정 (PUT /api/boards/{boardId}/comments/{commentId})
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto) {
        Long userId = 1L;
        commentService.updateComment(commentId, requestDto, userId);
        return ResponseEntity.ok("댓글이 성공적으로 수정 되었습니다.");
    }

    // 댓글 삭제 (DELETE /api/boards/{boardId}/comments/{commentId})
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId) {
        Long userId = 1L;
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제 되었습니다.");
    }
}
