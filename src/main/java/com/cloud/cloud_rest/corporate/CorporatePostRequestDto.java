package com.cloud.cloud_rest.corporate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CorporatePostRequestDto {

    @Getter
    @Setter
    public static class SaveDto {
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 비워둘 수 없습니다.")
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateDto {
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 비워둘 수 없습니다.")
        private String content;
    }
}
