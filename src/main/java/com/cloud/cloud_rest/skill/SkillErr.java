package com.cloud.cloud_rest.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SkillErr {

    SKILL_NOT_FOUND("SkillErr: 해당 기술항목 없음", HttpStatus.NOT_FOUND),
    SKILL_ALREADY_EXISTS("SkillErr: 이미 존재하는 기술항목", HttpStatus.BAD_REQUEST),
    SKILL_IN_USE("SkillErr: 현재 사용중인 기술항목", HttpStatus.BAD_REQUEST),
    SKILL_FORBIDDEN("SkillErr: 기술항목 수정권한 없음", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;
}
