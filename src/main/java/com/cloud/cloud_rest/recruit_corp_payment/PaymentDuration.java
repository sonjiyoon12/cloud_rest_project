package com.cloud.cloud_rest.recruit_corp_payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentDuration {
    WEEK(7),      // 7일
    MONTH(30),    // 30일
    QUARTER(90);  // 90일

    private final int days;

    // JSON 요청 본문(String)을 Enum으로 변환하기 위한 설정
    @JsonCreator
    public static PaymentDuration fromString(String key) {
        for (PaymentDuration duration : PaymentDuration.values()) {
            if (duration.name().equalsIgnoreCase(key)) {
                return duration;
            }
        }
        // 유효하지 않은 값이 들어오면 null을 반환 (나중에 @NotNull에 의해 처리됨)
        return null;
    }
}
