package com.cloud.cloud_rest.career;

import com.cloud.cloud_rest.resume.Resume;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public class CareerRequest {

    // 경력사항 저장
    @Schema(name = "CareerSaveRequest")
    @Data
    public static class SaveDTO {
        @NotBlank(message = "회사명을 입력해주세요")
        private String corpName;

        @Size(max = 20, message = "직책은 20자 이내로 작성해주세요")
        private String position;

        @NotBlank(message = "경력내용을 입력해주세요")
        @Size(max = 1000, message = "경력내용은 1000자 이내로 작성해주세요")
        private String content;

        private LocalDate startAt;
        private LocalDate endAt;

        public Career toEntity(Resume resume) {
            return Career.builder()
                    .corpName(this.corpName)
                    .position(this.position)
                    .content(this.content)
                    .startAt(this.startAt)
                    .endAt(this.endAt)
                    .resume(resume)
                    .build();
        }
    }

    // 경력 수정
    @Schema(name = "CareerUpdateRequest")
    @Data
    public static class UpdateDTO {
        private Long careerId;
        private String corpName;
        private String position;
        private String content;
        private LocalDate startAt;
        private LocalDate endAt;

        public Career toEntity(Resume resume) {
            return Career.builder()
                    .corpName(this.corpName)
                    .position(this.position)
                    .content(this.content)
                    .startAt(this.startAt)
                    .endAt(this.endAt)
                    .resume(resume)
                    .build();
        }
    }
}
