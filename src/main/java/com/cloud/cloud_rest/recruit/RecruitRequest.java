package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


public class RecruitRequest {

    @Getter
    @Setter
    public static class RecruitSaveDTO {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        private String image;

        @Size(max = 255, message = "메시지는 255자를 초과할 수 없습니다.")
        private String message;

        @FutureOrPresent(message = "마감일은 현재 날짜 또는 미래 날짜여야 합니다.")
        private LocalDate deadline;

        private List<Long> skillIds;

        public Recruit toEntity(Corp corp, String savedFileName) {
            return Recruit.builder()
                    .corp(corp)
                    .title(title)
                    .content(content)
                    .image(savedFileName)
                    .deadline(deadline)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class RecruitUpdateDTO {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        private String image;

        @FutureOrPresent(message = "마감일은 현재 날짜 또는 미래 날짜여야 합니다.")
        private LocalDate deadline;

        private List<Long> skillIds;
    }
}
