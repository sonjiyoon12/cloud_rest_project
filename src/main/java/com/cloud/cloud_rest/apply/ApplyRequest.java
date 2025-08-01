package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.resume.Resume;
import lombok.Data;

public class ApplyRequest {

    @Data
    public static class SaveDTO {

        public Apply toEntity(Resume resume, Recruit recruit) {
            return Apply.builder()
                    .resume(resume)
                    .recruit(recruit)
                    .build();
        }
    }
}
