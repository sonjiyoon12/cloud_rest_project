package com.cloud.cloud_rest.userskill;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* UserSkill 엔티티에서 user_id와 skill_id를 복합 기본키(Composite Primary Key) 로 만들고 싶다면,
* JPA에서는 @IdClass 또는 @EmbeddedId를 사용해야 합니다.
* */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillId implements Serializable {
    private Long userId;  // User 엔티티의 PK 타입
    private Long skillId; // Skill 엔티티의 PK 타입
}
