package com.cloud.cloud_rest.corporatePostLike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기업 게시물 좋아요 관련 요청을 위한 DTO
 * 좋아요 기능은 일반적으로 요청 본문(body)이 필요 없으므로 빈 클래스로 정의
 */
@Getter
@Setter
@NoArgsConstructor
public class CorporatePostLikeRequestDto {


    public static class SaveDto {
        // 좋아요 추가 시 사용될 DTO (현재는 필요 없음)
    }

    public static class DeleteDto {
        // 좋아요 취소 시 사용될 DTO (현재는 필요 없음)
    }
}