package com.cloud.cloud_rest.recruit_paid;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.recruit.Recruit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class RecruitPaidResponse {

    // 유료 공고 생성 결과 DTO
    @Getter
    @Setter
    public static class PaidSaveDTO {
        private Long recruitId;
        private LocalDate expiryDate;

        public PaidSaveDTO(RecruitPaid recruitPaid) {
            this.recruitId = recruitPaid.getRecruitId();
            this.expiryDate = recruitPaid.getExpiryDate();
        }
    }

    // 유료 공고 상태 조회 DTO
    @Getter
    @Setter
    public static class PaidDetailDTO {
        private boolean isPaid;
        private Long recruitId;
        private LocalDate expiryDate; // 유료일 경우 만료일, 아니면 null

        // 유료 공고 정보를 찾았을 때 사용하는 생성자
        public PaidDetailDTO(RecruitPaid recruitPaid) {
            this.isPaid = true;
            this.recruitId = recruitPaid.getRecruitId();
            this.expiryDate = recruitPaid.getExpiryDate();
        }

        // 유료 공고 정보를 못 찾았을 때 사용하는 생성자
        public PaidDetailDTO(Long recruitId) {
            this.isPaid = false;
            this.recruitId = recruitId;
            this.expiryDate = null;
        }
    }

    // [관리자] 전체 유료 공고 조회 DTO
    @Getter
    @Setter
    public static class PaidListDTO {
        private Long recruitId;
        private String title;
        private String corpName;
        private LocalDate paymentDate;
        private LocalDate expiryDate;

        public PaidListDTO(RecruitPaid recruitPaid) {
            Recruit recruit = recruitPaid.getRecruit();
            Corp corp = recruit.getCorp();

            this.recruitId = recruit.getRecruitId();
            this.title = recruit.getTitle();
            this.corpName = corp.getCorpName();
            this.paymentDate = recruitPaid.getPaymentDate();
            this.expiryDate = recruitPaid.getExpiryDate();
        }
    }
}
