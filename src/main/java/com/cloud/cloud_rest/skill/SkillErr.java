package com.cloud.cloud_rest.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SkillErr {

    SKILL_NOT_FOUND("해당 스킬을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SKILL_ALREADY_EXISTS("이미 존재하는 기술입니다.", HttpStatus.BAD_REQUEST),
    SKILL_IN_USE("현재 사용중인 스킬입니다.", HttpStatus.BAD_REQUEST),
    SKILL_FORBIDDEN("스킬을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;
}
