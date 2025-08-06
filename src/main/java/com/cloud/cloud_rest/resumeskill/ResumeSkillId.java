package com.cloud.cloud_rest.resumeskill;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ResumeSkillId implements Serializable {
    private Long resumeId;
    private Long skillId;

    public ResumeSkillId() {}

    public ResumeSkillId(Long resumeId, Long skillId){
        this.resumeId = resumeId;
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(!(object instanceof ResumeSkillId)) return false;
        ResumeSkillId resumeSkillId = (ResumeSkillId) object;
        return Objects.equals(resumeId, resumeSkillId.resumeId) &&
                Objects.equals(skillId, resumeSkillId.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resumeId, skillId);
    }
}
