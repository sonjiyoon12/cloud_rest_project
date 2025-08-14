package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.resume.Resume;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ApplyRequest {

    @Data
    @Schema(name = "applySaveRequest")
    public static class SaveDTO {

        private Long resumeId;
        private Long recruitId;

        public Apply toEntity(Resume resume, Recruit recruit) {
            return Apply.builder()
                    .resume(resume)
                    .recruit(recruit)
                    .build();
        }
    }

    // 기업이 이력서 검토하는 DTO
    @Schema(name = "ApplyReviewDTO")
    @Data
    public static class ReviewDTO {
        private String applyStatus;

        public String validate(Apply apply) {
            if (applyStatus == null || applyStatus.trim().isEmpty()) {
                throw new Exception400("검토는 필수입니다.");
            } else {
                for (ApplyStatus status : ApplyStatus.values()) {
                    if (applyStatus.toUpperCase().equals(status.toString()) && apply.getApplyStatus().toString().toUpperCase().equals(ApplyStatus.SUBMITTED.toString())) {
                        return applyStatus.toUpperCase();
                    }
                }
            }
            throw new Exception400("이미 검토했거나 잘못된 요청입니다.");
        }
    }
}
