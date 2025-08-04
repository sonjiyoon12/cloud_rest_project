package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.recruitskill.RecruitSkill;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruit_tb")
@Getter
@NoArgsConstructor
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long recruitId;

    private String title;

    @Lob
    private String content;

    private LocalDate deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Corp 엔티티와 다대일 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    @ToStringExclude
    private Corp corp;

    @OneToMany(mappedBy = "recruit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RecruitSkill> recruitSkills = new ArrayList<>();

    @Builder
    public Recruit(String title, String content, LocalDate deadline, Corp corp) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.corp = corp;
    }

    //소유권 확인 메서드
    public boolean isOwner(Corp corp) {
    return this.corp != null && this.corp.getCorpId().equals(corp.getCorpId());
    }

    //업데이트 메서드
    public void update(String title, String content, LocalDate deadline) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }
}
