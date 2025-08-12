package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-posts")
public class CorporatePostLikeController {

    private final CorporatePostLikeService postLikeService;

    @Auth(roles = {Role.CORP})
    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CorporatePostLikeResponseDto> toggleLike(@PathVariable Long postId,
                                                                @RequestAttribute("sessionUser") SessionUser sessionUser) {
        CorporatePostLikeResponseDto responseDto = postLikeService.togglePostLike(postId, sessionUser);
        return ApiResponse.success(responseDto);
    }
}