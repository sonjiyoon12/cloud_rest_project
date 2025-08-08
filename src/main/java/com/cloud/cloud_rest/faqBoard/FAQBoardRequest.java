package com.cloud.cloud_rest.faqBoard;

import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class FAQBoardRequest {

    // faq 저장
    @Schema(name = "faqSaveRequest")
    @Data
    public static class SaveDTO {
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;

        public FAQBoard toEntity(User user){
            return FAQBoard.builder()
                    .title(this.title)
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }

    // faq 수정
    @Schema(name = "faqUpdateRequest" )
    @Data
    public static class UpdateDTO{
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 100, message = "제목은 100자 이내로 작성해주세요")
        private String title;
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
    }

}
