package com.cloud.cloud_rest.recruit_paid;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class RecruitPaidRequest {

    //유료공고 생성 DTO
    @Getter
    @Setter
    public static class PaidSaveDTO {
        @NotNull(message = "공고 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long recruitId; // 유료로 전환할 공고의 ID

        @NotNull(message = "유료 서비스 기간은 필수입니다.")
        @Min(value = 1, message = "유료 서비스 기간은 최소 1일 이상이어야 합니다.")
        private Integer durationInDays; // 유료 서비스 기간 (일)
    }
}
