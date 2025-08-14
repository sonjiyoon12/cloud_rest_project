package com.cloud.cloud_rest.recruit_corp_payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class PaymentRequest {

    @Getter
    @Setter
    public static class PaymentSaveDTO {
        @NotNull(message = "공고 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long recruitId; // 결제할 공고의 ID

        @NotNull(message = "결제 기간은 필수입니다.")
        private PaymentDuration duration; // 결제 기간 (WEEK, MONTH, QUARTER)
    }
}
