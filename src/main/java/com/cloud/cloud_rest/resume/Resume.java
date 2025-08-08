package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.career.Career;
import com.cloud.cloud_rest.resumeskill.ResumeSkill;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "resume_tb")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    private String title;
    private String content;
    private String image;

    @Builder.Default
    private Boolean isRep = false;

    @CreationTimestamp
    private Timestamp createdAt;

    // User 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToStringExclude
    private User user;

    public boolean isOwner(Long checkUserId){
        return this.user.getUserId().equals(checkUserId);
    }

    public String getFormatTime() {
        return DateUtil.timestampFormat(createdAt);
    }

    // 수정 기능 추가
    public void update(ResumeRequest.UpdateDTO updateDTO, String image) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
        this.isRep = updateDTO.isRep();
        this.image = image;
    }

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeSkill> resumeSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();

    public void addCareer(Career career) {
        careers.add(career);
        career.setResume(this);
    }

    public void addResumeSkill(ResumeSkill resumeSkill) {
        this.resumeSkills.add(resumeSkill);
        resumeSkill.setResume(this);
    }
}
