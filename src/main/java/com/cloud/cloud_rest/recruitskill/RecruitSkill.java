package com.cloud.cloud_rest.recruitskill;

import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.skill.Skill;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recruitskill_tb")
@Getter
@NoArgsConstructor
public class RecruitSkill {

    @EmbeddedId // 복합키 식별자
    private RecruitSkillId recruitSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recruitId") // 복합키의 recruitId와 매핑
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId") // 복합키의 skillId와 매핑
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    // 생성자 -> 연관관계 주입
    public RecruitSkill(Recruit recruit, Skill skill) {
        this.recruit = recruit;
        this.skill = skill;
        this.recruitSkillId = new RecruitSkillId(recruit.getRecruitId(), skill.getSkillId());
    }
}
