package com.cloud.cloud_rest.bulletin;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "bulletin_tb")
@Entity
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulletin_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String imagePath;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public boolean isOwner(Long sessionUserId) {
        return this.user.getUserId().equals(sessionUserId);
    }

    public String getFormatTime() {
        return DateUtil.timestampFormat(createdAt);
    }
}
