package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-posts/{postId}/comments")
public class CorporateCommentController {

    private final CorporateCommentService commentService;

    @Auth(roles = {Role.COMPANY})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> saveComment(@PathVariable Long postId,
                                         @Valid @RequestBody CorporateCommentRequestDto.SaveDto saveDto,
                                         @AuthenticationPrincipal User sessionUser) {
        commentService.saveComment(postId, saveDto, sessionUser);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<CorporateCommentResponseDto.CommentDto>> getComments(@PathVariable Long postId,
                                                                                  @AuthenticationPrincipal User sessionUser) {
        List<CorporateCommentResponseDto.CommentDto> comments = commentService.findCommentsByPostId(postId, sessionUser);
        return ApiResponse.success(comments);
    }

    @Auth(roles = {Role.COMPANY})
    @PutMapping("/{commentId}")
    public ApiResponse<Void> updateComment(@PathVariable Long postId,
                                         @PathVariable Long commentId,
                                         @Valid @RequestBody CorporateCommentRequestDto.UpdateDto updateDto,
                                         @AuthenticationPrincipal User sessionUser) {
        commentService.updateComment(commentId, updateDto, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.COMPANY})
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId,
                                         @AuthenticationPrincipal User sessionUser) {
        commentService.deleteComment(commentId, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.COMPANY})
    @PostMapping("/{commentId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> toggleCommentLike(@PathVariable Long commentId,
                                               @AuthenticationPrincipal User sessionUser) {
        commentService.toggleCommentLike(commentId, sessionUser);
        return ApiResponse.success();
    }
}