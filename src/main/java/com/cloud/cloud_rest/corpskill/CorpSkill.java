package com.cloud.cloud_rest.corpskill;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.skill.Skill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "corp_skill_tb")
public class CorpSkill {

    @EmbeddedId // user_id와 skill_id를 복합 기본키(Composite Primary Key) 만들기
    private CorpSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("corpId")
    @JoinColumn(name = "corp_id")
    private Corp corp;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId") // EmbeddedId의 skillId 매핑
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public CorpSkill(Corp corp, Skill skill) {
        this.corp = corp;
        this.skill = skill;
        this.id = new CorpSkillId(corp.getCorpId(),skill.getSkillId());
    }
}
