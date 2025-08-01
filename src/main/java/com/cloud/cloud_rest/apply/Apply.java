package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@Entity
@Data
@Table(name = "apply_tb")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @JoinColumn(name = "recruit_id" , nullable = false)
    private Recruit recruit;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }
}
