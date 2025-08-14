package com.cloud.cloud_rest.recruit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecruitErr {

    CORP_NOT_FOUND("RecruitErr: 해당 기업 없음", HttpStatus.NOT_FOUND),
    RECRUIT_NOT_FOUND("RecruitErr: 해당 공고 없음", HttpStatus.NOT_FOUND),
    RECRUIT_FORBIDDEN("RecruitErr: 공고 수정권한 없음", HttpStatus.FORBIDDEN),
    IMAGE_PROCESS_FAILED("RecruitErr: 채용공고 이미지 처리 실패", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
