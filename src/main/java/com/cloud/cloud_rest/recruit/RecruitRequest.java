package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


public class RecruitRequest {

    @Getter
    @Setter
    public static class RecruitSaveDTO {

        private String title;
        private String content;
        private String image;
        private String message;
        private LocalDate deadline;
        private List<Long> skillIds;

        public Recruit toEntity(Corp corp, String savedFileName) {
            return Recruit.builder()
                    .corp(corp)
                    .title(title)
                    .content(content)
                    .image(savedFileName)
                    .deadline(deadline)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class RecruitUpdateDTO {
        private String title;
        private String content;
        private String image;
        private LocalDate deadline;
        private List<Long> skillIds;
    }
}