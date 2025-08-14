package com.cloud.cloud_rest.recruit_skill;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable // 복합키로 사용할 식별자 클래스
public class RecruitSkillId implements Serializable {
    private Long recruitId; // 공고 PK
    private Long skillId; // 스킬 PK

    public RecruitSkillId() {
    }

    public RecruitSkillId(Long recruitId, Long skillId) {
        this.recruitId = recruitId;
        this.skillId = skillId;
    }

    // 두 객체의 동등성을 비교 (JPA 에서 복합키 쓰려면 필수)
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof RecruitSkillId)) return false;

        RecruitSkillId recruitSkillId = (RecruitSkillId) obj;

        return Objects.equals(recruitId, recruitSkillId.recruitId)
                && Objects.equals(skillId, recruitSkillId.skillId);
    }

    //해시코드 생성 (JPA 에서 복합키 쓰려면 필수)
    @Override
    public int hashCode() {
        return Objects.hash(recruitId, skillId);
    }
}
