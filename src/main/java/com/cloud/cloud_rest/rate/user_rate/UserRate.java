package com.cloud.cloud_rest.rate.user_rate;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@Table(name = "user_rate_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "corp_id"})})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_id", nullable = false)
    private Corp corp;

    private Long rating;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }
}
