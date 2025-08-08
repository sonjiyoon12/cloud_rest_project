package com.cloud.cloud_rest.recruitpaid;

import com.cloud.cloud_rest.recruit.Recruit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "recruit_paid_tb")
public class RecruitPaid {

    @Id
    private Long recruitId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @Column(nullable = false)
    private LocalDate expiryDate; // 만료일

    @CreationTimestamp
    private LocalDate paymentDate; // 결제일

    // 생성 로직을 정적 팩토리 메소드로 이전
    private RecruitPaid(Recruit recruit, LocalDate expiryDate) {
        this.recruit = recruit;
        this.expiryDate = expiryDate;
    }

    //만료일 계산 메서드
    public static RecruitPaid create(Recruit recruit, Integer durationInDays) {
        LocalDate expiryDate = LocalDate.now().plusDays(durationInDays);
        return new RecruitPaid(recruit, expiryDate);
    }
}
