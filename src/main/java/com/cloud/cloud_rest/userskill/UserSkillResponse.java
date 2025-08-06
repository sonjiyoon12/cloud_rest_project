package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corpskill.CorpSkill;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserSkillResponse {

    @Data
    public static class UserSkillDTO {
        private Long corpId;
        private String corpName;
        private String email;
        private List<String> corpSkills;

        @Builder
        public UserSkillDTO(Corp corp){
            this.corpId = corp.getCorpId();
            this.corpName = corp.getCorpName();
            this.email = corp.getEmail();
            this.corpSkills = corp.getCorpSkills().stream()
                    .map(cs -> cs.getSkill().getName())
                    .collect(Collectors.toList());
        }
    }
}
