package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


public class RecruitRequest {

    @Getter
    @Setter
    public static class SaveDTO {

        private String title;
        private String content;
        private LocalDate deadline;
        private List<Long> skillIds;

        public Recruit toEntity(Corp corp) {
            return Recruit.builder()
                    .corp(corp)
                    .title(title)
                    .content(content)
                    .deadline(deadline)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String title;
        private String content;
        private LocalDate deadline;
        private List<Long> skillIds;
    }
}