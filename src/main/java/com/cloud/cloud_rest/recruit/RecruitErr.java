package com.cloud.cloud_rest.recruit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecruitErr {

    CORP_NOT_FOUND("해당 기업 없음", HttpStatus.NOT_FOUND),
    RECRUIT_NOT_FOUND("해당 공고 없음", HttpStatus.NOT_FOUND),
    ACCESS_DENIED("접근 권한 없음", HttpStatus.FORBIDDEN),
    NO_AUTHORITY_TO_UPDATE("공고를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NO_AUTHORITY_TO_DELETE("공고를 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    DEADLINE_IS_PAST("마감일은 과거 날짜로 설정할 수 없습니다.", HttpStatus.BAD_REQUEST),
    RECRUIT_IS_CLOSED("이미 마감된 채용공고입니다.", HttpStatus.BAD_REQUEST);


    private final String message;
    private final HttpStatus status;
}
