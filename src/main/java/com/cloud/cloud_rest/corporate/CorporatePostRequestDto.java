package com.cloud.cloud_rest.corporate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CorporatePostRequestDto {

    @Getter
    @Setter
    public static class SaveDto {
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 비워둘 수 없습니다.")
        private String content;

        private List<String> tags;
    }

    @Getter
    @Setter
    public static class UpdateDto {
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 비워둘 수 없습니다.")
        private String content;

        private List<String> tags;
    }

    // 태그 검색용 DTO
    @Getter
    @Setter
    public static class SearchDTO {
        private String keyword;
        private java.util.List<String> corporateTags;

        public boolean hasKeyword() {
            return keyword != null && !keyword.trim().isEmpty();
        }
        public boolean hasTags() {
            return corporateTags != null && !corporateTags.isEmpty();
        }
    }
}
