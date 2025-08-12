package com.cloud.cloud_rest.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BoardRequestDto {

    @Getter
    @Setter
    public static class SaveDto {
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        private String title;
        @NotBlank(message = "내용은 비워둘 수 없습니다.")
        private String content;
        private List<String> tags;
        private MultipartFile image;
    }

    @Getter
    @Setter
    public static class UpdateDto {
        private String title;
        private String content;
        private List<String> tags;
        private MultipartFile image;
    }

    @Getter
    @Setter
    public static class SearchDTO {
        private String keyword;
        private List<String> tags;
    }
}