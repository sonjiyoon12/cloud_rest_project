package com.cloud.cloud_rest.skill;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "skill_tb")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String skillName;

    @Builder
    public Skill(Long id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }

    // 업데이트 메소드
    public void update(String skillName) {
        this.skillName = skillName;
    }
}
