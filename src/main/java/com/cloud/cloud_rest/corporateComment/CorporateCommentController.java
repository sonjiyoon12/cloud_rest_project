package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-posts/{postId}/comments")
public class CorporateCommentController {

    private final CorporateCommentService commentService;

    @Auth(roles = {Role.CORP})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> saveComment(@PathVariable Long postId,
                                         @Valid @RequestBody CorporateCommentRequestDto.SaveDto saveDto,
                                         @RequestAttribute SessionUser sessionUser) {
        commentService.saveComment(postId, saveDto, sessionUser);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<CorporateCommentResponseDto.CommentDto>> getComments(@PathVariable Long postId,
                                                                                 @RequestAttribute SessionUser sessionUser) {
        List<CorporateCommentResponseDto.CommentDto> comments = commentService.findCommentsByPostId(postId, sessionUser);
        return ApiResponse.success(comments);
    }

    @Auth(roles = {Role.CORP})
    @PutMapping("/{commentId}")
    public ApiResponse<Void> updateComment(@PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @Valid @RequestBody CorporateCommentRequestDto.UpdateDto updateDto,
                                           @RequestAttribute SessionUser sessionUser) {
        commentService.updateComment(commentId, updateDto, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.CORP})
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId,
                                           @RequestAttribute SessionUser sessionUser) {
        commentService.deleteComment(commentId, sessionUser);
        return ApiResponse.success();
    }
}