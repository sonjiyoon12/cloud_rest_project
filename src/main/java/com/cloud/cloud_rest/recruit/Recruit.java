package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.recruitskill.RecruitSkill;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDate deadLine;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Corp 엔티티와 다대일 연관관계 설정
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "corp_id", nullable = false)
    //private Corp corp;

    private Long corpId; //todo 연관관계 설정

    @OneToMany(mappedBy = "recruit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RecruitSkill> recruitSkills = new ArrayList<>();

    @Builder
    public Recruit(String title, String content, LocalDate deadLine, Long corpId) {
        this.title = title;
        this.content = content;
        this.deadLine = deadLine;
        this.corpId = corpId;
    }

    //소유권 확인 메서드
    //public boolean isOwner(Long corpId) {
    //return this.corp != null && this.corp.getId().equals(corpId);
    //}
}
