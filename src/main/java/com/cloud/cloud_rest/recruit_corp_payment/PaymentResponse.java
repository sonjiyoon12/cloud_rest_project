package com.cloud.cloud_rest.recruit_corp_payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PaymentResponse {

    @Getter
    @Setter
    @Builder
    public static class PaymentSaveDTO {
        private Long paymentId;
        private Long recruitId;
        private PaymentDuration duration;
    }

    @Getter
    @Setter
    @Builder
    public static class PaymentDetailDTO {
        private Long paymentId;
        private Long recruitId;
        private PaymentDuration duration;
        private String createdAt;
    }
}
