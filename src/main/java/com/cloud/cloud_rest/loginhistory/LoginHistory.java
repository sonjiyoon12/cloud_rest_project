package com.cloud.cloud_rest.loginhistory;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "login_history_tb")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Corp corp;

    @CreationTimestamp
    private Timestamp loginTime;

    private String ipAddress;

    private String userAgent;

    @Builder.Default
    private boolean isActive = true;

    public void deactivate() {
        this.isActive = false;
    }

    public String getTime(){
        return DateUtil.timestampFormat(loginTime);
    }
}
