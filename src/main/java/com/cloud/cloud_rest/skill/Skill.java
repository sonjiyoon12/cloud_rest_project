package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest.corpskill.CorpSkill;
import com.cloud.cloud_rest.recruit_skill.RecruitSkill;
import com.cloud.cloud_rest.userskill.UserSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.cloud.cloud_rest.resumeskill.ResumeSkill;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skill_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "skill",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RecruitSkill> recruitSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<ResumeSkill> resumeSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill" , cascade = CascadeType.ALL)
    private List<UserSkill> userSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill" , cascade = CascadeType.ALL)
    private List<CorpSkill> corpSkills = new ArrayList<>();

    public Skill(String name) {
        this.name = name;
    }

    // 관리자용 수정기능
    public void updateName(String newName) {
        this.name = newName;
    }
}
