package com.cloud.cloud_rest.skill;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class SkillRequest {

    @Getter
    @Setter
    public static class SkillSaveDTO {
        @NotEmpty
        private String name;

        public Skill toEntity() {
            return Skill.builder()
                    .name(this.name)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class SkillUpdateDTO {
        @NotEmpty
        private String name;
    }
}
