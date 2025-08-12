package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PostMapping("/boards/{boardId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> saveComment(@PathVariable Long boardId,
                                         @Valid @RequestBody CommentRequestDto.SaveDto saveDto,
                                         @RequestAttribute("sessionUser") SessionUser sessionUser) {
        commentService.saveComment(boardId, saveDto, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PutMapping("/comments/{commentId}")
    public ApiResponse<Void> updateComment(@PathVariable Long commentId,
                                           @Valid @RequestBody CommentRequestDto.UpdateDto updateDto,
                                           @RequestAttribute("sessionUser") SessionUser sessionUser) {
        commentService.updateComment(commentId, updateDto, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.USER, Role.ADMIN})
    @DeleteMapping("/comments/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId,
                                           @RequestAttribute("sessionUser") SessionUser sessionUser) {
        commentService.deleteComment(commentId, sessionUser);
        return ApiResponse.success();
    }
}