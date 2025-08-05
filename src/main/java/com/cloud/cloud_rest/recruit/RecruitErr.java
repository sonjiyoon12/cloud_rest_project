package com.cloud.cloud_rest.recruit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecruitErr {

    CORP_NOT_FOUND("해당 기업 없음", HttpStatus.NOT_FOUND),
    RECRUIT_NOT_FOUND("해당 공고 없음", HttpStatus.NOT_FOUND),
    RECRUIT_FORBIDDEN("공고를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);


    private final String message;
    private final HttpStatus status;
}
