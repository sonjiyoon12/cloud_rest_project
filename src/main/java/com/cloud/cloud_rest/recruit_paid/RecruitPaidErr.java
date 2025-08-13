package com.cloud.cloud_rest.recruit_paid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum RecruitPaidErr {
    FORBIDDEN(HttpStatus.FORBIDDEN, "RecruitPaidErr: 내 공고만 바꿀수 있습니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "RecruitPaidErr: 이미 유료 공고입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "RecruitPaidErr: 삭제 대상이 없습니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
