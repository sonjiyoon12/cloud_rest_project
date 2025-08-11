package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-comments")
public class CorporateCommentLikeController {
    private final CorporateCommentLikeService commentLikeService;

    @Auth(roles = {Role.CORP})
    @PostMapping("/{commentId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CorporateCommentLikeResponseDto> toggleCommentLike(@PathVariable Long commentId,
                                                                          @RequestAttribute User sessionUser) {
        CorporateCommentLikeResponseDto responseDto = commentLikeService.toggleCommentLike(commentId, sessionUser);
        return ApiResponse.success(responseDto);
    }
}
