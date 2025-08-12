package com.cloud.cloud_rest.resumeskill;

import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.skill.Skill;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "resume_skill_tb")
@Entity
@EqualsAndHashCode(exclude = {"resume", "skill"})
public class ResumeSkill {

    @EmbeddedId
    private ResumeSkillId resumeSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("resumeId")
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public ResumeSkill(Resume resume, Skill skill) {
        this.resume = resume;
        this.skill = skill;
        this.resumeSkillId = new ResumeSkillId(resume.getResumeId(), skill.getSkillId());
    }
}
