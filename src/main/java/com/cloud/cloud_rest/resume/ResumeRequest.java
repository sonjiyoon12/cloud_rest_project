package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ResumeRequest {

    // 이력서 저장 DTO
    @Data
    public static class ResumeSaveDTO {
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
        private boolean isRep = false;
        private List<Long> skillIds = new ArrayList<>();

        public Resume toEntity(User user) {
            return Resume.builder()
                    .title(this.title)
                    .content(this.content)
                    .isRep(this.isRep)
                    .user(user)
                    .build();
        }
    }

    // 이력서 수정 DTO
    @Data
    public static class ResumeUpdateDTO{
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
        private boolean isRep = false;
        private List<Long> skillIds = new ArrayList<>();

    }
}
