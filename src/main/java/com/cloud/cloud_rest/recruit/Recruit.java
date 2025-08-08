package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.noti.Noti;
import com.cloud.cloud_rest.recruitpaid.RecruitPaid;
import com.cloud.cloud_rest.recruitskill.RecruitSkill;
import com.cloud.cloud_rest.skill.Skill;
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
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    @ToStringExclude
    private Corp corp;

    @OneToMany(mappedBy = "recruit")
    private List<Noti> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "recruit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RecruitSkill> recruitSkills = new ArrayList<>();

    @OneToOne(mappedBy = "recruit", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private RecruitPaid recruitPaid;

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
    public void update(RecruitRequest.RecruitUpdateDTO dto, List<Skill> newSkills) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.deadline = dto.getDeadline();
        updateSkills(newSkills);
    }

    // 스킬 업데이트
    public void updateSkills(List<Skill> newSkills) {
        // 새로운 스킬ID 목록을 Set으로 만든다
        Set<Long> newSkillIds = newSkills.stream()
                .map(Skill::getSkillId)
                .collect(Collectors.toSet());

        // 기존 스킬 목록에서, 새로운 목록에 없는 스킬들을 제거
        this.recruitSkills.removeIf(recruitSkill -> !newSkillIds.contains(recruitSkill.getSkill().getSkillId()));

        // 현재 스킬 ID 목록을 다시 만듬
        Set<Long> currentSkillIds = this.recruitSkills.stream()
                .map(recruitSkill -> recruitSkill.getSkill().getSkillId())
                .collect(Collectors.toSet());

        // 새로운 스킬 목록을 순회하며, 현재 목록에 없는 스킬만 추가
        for (Skill skill : newSkills) {
            if (!currentSkillIds.contains(skill.getSkillId())) {
                this.recruitSkills.add(new RecruitSkill(this, skill));
            }
        }
    }
}
