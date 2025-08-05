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
    private Long skillId;

    @Column(unique = true)
    private String name;

    @Builder
    public Skill(Long skillId, String name) {
        this.skillId = skillId;
        this.name = name;
    }

    // 업데이트 메소드
    public void update(String name) {
        this.name = name;
    }
}
