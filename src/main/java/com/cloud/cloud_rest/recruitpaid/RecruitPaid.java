package com.cloud.cloud_rest.recruitpaid;

import com.cloud.cloud_rest.recruit.Recruit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Long recruitId; // PK. Recruit의 PK 타입(Integer)과 일치시킵니다.

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // recruit 필드의 ID를 이 엔티티의 PK(@Id)에 매핑합니다.
    @JoinColumn(name = "recruit_id") // DB에 생성될 컬럼 이름입니다. 이 컬럼이 PK이자 FK가 됩니다.
    private Recruit recruit;

    @Column(nullable = false)
    private LocalDate expiryDate; // 유료 공고 만료일

    @CreationTimestamp
    private LocalDate paymentDate; // 결제일 (생성일)

    @Builder
    public RecruitPaid(Recruit recruit, LocalDate expiryDate) {
        this.recruit = recruit;
        this.expiryDate = expiryDate;
    }
}
