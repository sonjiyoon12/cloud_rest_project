package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PostMapping("/{commentId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CommentLikeResponseDto> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestAttribute("sessionUser") SessionUser sessionUser) {
        CommentLikeResponseDto responseDto = commentLikeService.toggleCommentLike(commentId, sessionUser);
        return ApiResponse.success(responseDto);
    }
}