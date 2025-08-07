package com.cloud.cloud_rest.CommentLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 좋아요 등록 및 취소 (POST /api/comments/{commentId}/likes)
    @PostMapping
    public ResponseEntity<?> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestBody CommentLikeRequestDto.SaveDto requestDto) {
        Long userId = requestDto.getUserId();

        try {
            // 좋아요 등록 또는 취소
            CommentLike commentLike = commentLikeService.toggleCommentLike(commentId, userId);

            if (commentLike == null) {
                
                return ResponseEntity.noContent().build();
            }


            Map<String, Object> response = new HashMap<>();
            response.put("id", commentLike.getId());
            response.put("commentId", commentLike.getComment().getCommentId());
            response.put("userId", commentLike.getUser().getUserId());
            response.put("likedAt", commentLike.getLikedAt());

            // 201 Created 응답과 함께 Map을 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

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