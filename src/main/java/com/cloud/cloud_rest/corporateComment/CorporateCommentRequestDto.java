package com.cloud.cloud_rest.corporateComment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CorporateCommentRequestDto {

    @Getter
    @Setter
    public static class SaveDto {
        @NotBlank(message = "댓글 내용은 비워둘 수 없습니다.")
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateDto {
        @NotBlank(message = "댓글 내용은 비워둘 수 없습니다.")
        private String content;
    }
}