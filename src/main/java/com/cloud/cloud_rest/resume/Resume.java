package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._define.DateUtil;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
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
    private Boolean isRep = false;

    @CreationTimestamp
    private Timestamp createdAt;

    // User 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Resume(Long resumeId, String title, String content, Boolean isRep, Timestamp createdAt, User user) {
        this.resumeId = resumeId;
        this.title = title;
        this.content = content;
        this.isRep = isRep;
        this.createdAt = createdAt;
        this.user = user;
    }

    public boolean isOwner(Long checkUserId){
        return this.user.getUserId().equals(checkUserId);
    }

    public String getTime() {
        return null;
    }
}
