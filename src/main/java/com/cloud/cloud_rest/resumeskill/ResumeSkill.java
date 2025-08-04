package com.cloud.cloud_rest.resumeskill;

import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.skill.Skill;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "resume_skill_tb")
@Entity
public class ResumeSkill {

    @EmbeddedId
    private ResumeSkillId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("resumeId")
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

}
