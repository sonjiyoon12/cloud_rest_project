package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest.career.CareerRequest;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

public class ResumeRequest {

    // 이력서 저장 DTO
    @Schema(name = "resumeSaveRequest")
    @Data
    public static class SaveDTO {
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
        private String image;
        private List<Long> skillIds = new ArrayList<>();
        private List<CareerRequest.SaveDTO> careers = new ArrayList<>();

        public Resume toEntity(User user, String image) {
            return Resume.builder()
                    .title(this.title)
                    .content(this.content)
                    .image(image)
                    .user(user)
                    .build();
        }
    }

    // 이력서 수정 DTO
    @Schema(name = "resumeUpdateRequest")
    @Data
    public static class UpdateDTO {
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
        private String image;
        private List<Long> skillIds = new ArrayList<>();
        private List<CareerRequest.UpdateDTO> careers = new ArrayList<>();
    }
}
