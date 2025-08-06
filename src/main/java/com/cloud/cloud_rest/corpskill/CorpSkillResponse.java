package com.cloud.cloud_rest.corpskill;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.userskill.UserSkill;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CorpSkillResponse {

    @Data
    public static class CorpSkillDTO {
        private Long userId;
        private String userName;
        private String email;
        private List<String> userSkills;

        public CorpSkillDTO(User user) {
            this.userId = user.getUserId();
            this.userName = user.getUsername();
            this.email = user.getEmail();
            this.userSkills = user.getUserSkills().stream()
                    .map(us -> us.getSkill().getName())
                    .toList();
        }
    }
}
