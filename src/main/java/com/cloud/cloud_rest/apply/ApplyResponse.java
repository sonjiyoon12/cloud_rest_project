package com.cloud.cloud_rest.apply;

import lombok.Builder;
import lombok.Data;

public class ApplyResponse {

    @Data
    public static class SaveDTO {
        private Long id;
        private Long resumeId;
        private Long recruitId;
        private String createdAt;

        @Builder
        public SaveDTO(Apply apply) {
            this.id = apply.getApplyId();
            this.resumeId = apply.getResume().getResumeId();
            this.recruitId = apply.getRecruit().getId();
            this.createdAt = apply.getTime();
        }
    }

    @Data
    public static class DetailDTO {
        private Long id;
        private ResumeDTO resume;
        private RecruitDTO recruit;

        @Builder
        public DetailDTO(Apply apply) {

        }
    }

    @Data
    public static class ResumeDTO {

    }

    @Data
    public static class RecruitDTO {

    }
}
