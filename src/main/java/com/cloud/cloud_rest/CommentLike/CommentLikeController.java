package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 좋아요 등록 및 취소 (POST /api/comments/{commentId}/likes)
    @PostMapping
    @Auth
    public ResponseEntity<?> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestBody CommentLikeRequestDto.SaveDto requestDto) {
        Long userId = requestDto.getUserId();

        try {
            CommentLikeWithCountDto responseDto = commentLikeService.toggleCommentLike(commentId, userId);

            // 좋아요 취소 시 서비스는 null을 반환
            if (responseDto == null) {
                return ResponseEntity.noContent().build();
            }

            // 좋아요 등록 시 201 Created 응답과 함께 DTO를 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(responseDto));

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // 특정 댓글의 좋아요 개수 조회 (GET /api/comments/{commentId}/likes/count)
    @GetMapping("/count")
    public ResponseEntity<Long> getCommentLikeCount(@PathVariable Long commentId) {
        try {
            long likeCount = commentLikeService.getCommentLikeCount(commentId);
            return ResponseEntity.ok(likeCount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}