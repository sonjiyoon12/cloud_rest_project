package com.cloud.cloud_rest.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SkillResponse {

    @Getter
    @AllArgsConstructor
    public static class SkillListDTO {

        private Long skillId;
        private String name;

        // 정적 팩토리 메서드 (엔티티 → DTO 변환)
        public static SkillListDTO of(Skill skill) {
            return new SkillListDTO(
                    skill.getSkillId(),
                    skill.getName()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class SkillDetailDTO {

        private Long skillId;
        private String name;

        // 정적 팩토리 메서드 (엔티티 → DTO 변환)
        public static SkillDetailDTO of(Skill skill) {
            return new SkillDetailDTO(
                    skill.getSkillId(),
                    skill.getName()
            );
        }
    }

}
