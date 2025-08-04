package com.cloud.cloud_rest.recruit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        private String corpName;

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

    // 상세 조회용 DTO 추가
    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecruitDetailDTO {
        private Long recruitId;
        private String title;
        private String content;
        private LocalDate deadline;
        private LocalDateTime createdAt;
        private Long corpId;
        private String corpName;
        private List<String> skills; // 기술 스택 정보 추가

        // 정적 팩토리 메서드
        public static RecruitDetailDTO of(Recruit recruit) {
            RecruitDetailDTO dto = new RecruitDetailDTO();
            dto.setRecruitId(recruit.getRecruitId());
            dto.setTitle(recruit.getTitle());
            dto.setContent(recruit.getContent());
            dto.setDeadline(recruit.getDeadline());
            dto.setCreatedAt(recruit.getCreatedAt());
            dto.setCorpId(recruit.getCorp().getCorpId());
            dto.setCorpName(recruit.getCorp().getCorpName());
            dto.setSkills(
                recruit.getRecruitSkills().stream()
                        .map(recruitSkill -> recruitSkill.getSkill().getName())
                        .collect(Collectors.toList())
            );
            return dto;
        }
    }
}
