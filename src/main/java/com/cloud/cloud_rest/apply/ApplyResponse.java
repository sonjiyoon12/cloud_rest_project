package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.resume.Resume;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class ApplyResponse {

    @Schema(name = "ApplySaveRequest")
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
            this.recruitId = apply.getRecruit().getRecruitId();
            this.createdAt = apply.getTime();
        }
    }

    @Schema(name = "ApplyDetailRequest")
    @Data
    public static class DetailDTO {
        private Long id;
        private ResumeDTO resume;
        private RecruitDTO recruit;
        private ApplyStatus applyStatus;
        private String createdAt;

        @Builder
        public DetailDTO(Apply apply) {
            this.id = apply.getApplyId();
            this.resume = new ResumeDTO(apply.getResume());
            this.recruit = new RecruitDTO(apply.getRecruit());
            this.applyStatus = apply.getApplyStatus();
            this.createdAt = apply.getTime();
        }
    }

    @Schema(name = "ApplyResumeDTO")
    @Data
    public static class ResumeDTO {
        private String title;
        private String content;
        private String createdAt;

        @Builder
        public ResumeDTO(Resume resume) {
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.createdAt = resume.getCreatedAt().toString();
        }
    }

    @Schema(name = "ApplyRecruitDTO")
    @Data
    public static class RecruitDTO {
        private String title;
        private String content;
        private LocalDate deadLine;
        private String createdAt;

        @Builder
        public RecruitDTO(Recruit recruit) {
            this.title = recruit.getTitle();
            this.content = recruit.getContent();
            this.deadLine = recruit.getDeadline();
            this.createdAt = recruit.getCreatedAt().toString();
        }
    }


}
