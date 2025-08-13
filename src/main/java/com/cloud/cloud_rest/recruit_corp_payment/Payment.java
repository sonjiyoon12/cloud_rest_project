package com.cloud.cloud_rest.recruit_corp_payment;

import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.recruit.Recruit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "payment_tb")
public class Payment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit recruit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentDuration duration;

    @Builder
    public Payment(Recruit recruit, PaymentDuration duration) {
        this.recruit = recruit;
        this.duration = duration;
    }
}
