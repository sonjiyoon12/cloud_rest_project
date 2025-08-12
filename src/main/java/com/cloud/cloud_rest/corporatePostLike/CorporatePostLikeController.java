package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-post-likes")
public class CorporatePostLikeController {

    private final CorporatePostLikeService corporatePostLikeService;

    /**
     * 기업 게시물 좋아요/좋아요 취소
     * @param postId 좋아요를 처리할 게시물 ID
     * @param sessionUser 현재 로그인된 사용자 정보
     * @return 성공 응답
     */
    @Auth(roles = {Role.CORP})
    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> toggleLike(@PathVariable Long postId,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) {
        corporatePostLikeService.toggleLike(postId, sessionUser);
        return ApiResponse.success();
    }
}