package com.cloud.cloud_rest.recruit;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "corp_tb")
@Getter
@NoArgsConstructor
public class Corp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corp_id")
    private Long corpId;

    @Column(name = "corp_name")
    private String corpName;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "corp_image")
    private String corpImage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Corp(Long corpId, String corpName, String loginId, String password, String email, String corpImage) {
        this.corpId = corpId;
        this.corpName = corpName;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.corpImage = corpImage;
    }
}
