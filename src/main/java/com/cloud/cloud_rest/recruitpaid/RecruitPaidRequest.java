package com.cloud.cloud_rest.recruitpaid;

import lombok.Getter;
import lombok.Setter;

public class RecruitPaidRequest {

    //유료공고 생성 DTO
    @Getter
    @Setter
    public static class PaidSaveDTO {
        private Long recruitId; // 유료로 전환할 공고의 ID
        private Integer durationInDays; // 유료 서비스 기간 (일)
    }
}
