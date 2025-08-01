package com.cloud.cloud_rest.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_tb")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String loginId;
    private String password;
    private String email;
    private String phoneNumber;
    private String sex;
    private int age;
    private String userImage;
    private String address;
}
