package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpStatus;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "corp_approval")
public class CorpApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corpApprovalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id")
    private Corp corp;

    @Enumerated(EnumType.STRING)
    private CorpStatus corpStatus;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime(){
        return DateUtil.timestampFormat(createdAt);
    }

}
