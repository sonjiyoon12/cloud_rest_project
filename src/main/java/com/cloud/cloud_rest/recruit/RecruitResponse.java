package com.cloud.cloud_rest.recruit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecruitResponse {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RecruitListDTO {

        private Long recruitId;
        private String title;
        private String content;
        private LocalDate deadline;
        private LocalDateTime createdAt;
        private Long corpId;
        private String corpNmae;

        // 정적 팩토리 메서드 (엔티티 → DTO 변환)
        public static RecruitListDTO of(Recruit recruit) {
            return new RecruitListDTO(
                    recruit.getRecruitId(),
                    recruit.getTitle(),
                    recruit.getContent(),
                    recruit.getDeadline(),
                    recruit.getCreatedAt(),
                    recruit.getCorp().getCorpId(),
                    recruit.getCorp().getCorpName()
            );
        }
    }
}
