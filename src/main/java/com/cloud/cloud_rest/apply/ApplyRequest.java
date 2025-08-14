package com.cloud.cloud_rest.apply;

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
}
