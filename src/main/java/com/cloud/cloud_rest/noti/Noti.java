package com.cloud.cloud_rest.noti;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "notification_tb")
@Entity
public class Noti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    /*
    @OneToMany
    private Long corpId;

    @OneToMany
    private Long userId;
    */
    private String message;
    private boolean isRead;

    @CreationTimestamp
    private Timestamp creatdAt;

    @Builder
    public Noti(Long notificationId, Long corpId, Long userId, String message, boolean isRead, Timestamp creatdAt) {
        this.notificationId = notificationId;
        //this.corpId = corpId;
        //this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.creatdAt = creatdAt;
    }
}
