package com.cloud.cloud_rest.skill;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skill_tb")
@Getter
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Skill(String name) {
        this.name = name;
    }

    // 관리자용 수정기능
    public void updateName(String newName) {
        this.name = newName;
    }
}
