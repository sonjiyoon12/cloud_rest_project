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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public RecruitSkill(Recruit recruit, Skill skill) {
        this.recruit = recruit;
        this.skill = skill;
    }
}
