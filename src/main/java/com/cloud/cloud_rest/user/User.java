package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.userskill.UserSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private String userImage = "basic.png";

    private String address;
    private String addressDefault;
    private String addressDetail;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getFormatTime(){
        return DateUtil.timestampFormat(createdAt);
    }


    public void update(UserRequest.UpdateDTO updateDTO, String userUploadImage) {
        if (updateDTO.getUsername() != null) this.username = updateDTO.getUsername();
        if (updateDTO.getPhoneNumber() != null) this.phoneNumber = updateDTO.getPhoneNumber();
        if (userUploadImage != null) this.userImage = userUploadImage; // MultipartFile 여부와 무관
        if (updateDTO.getAddress() != null) this.address = updateDTO.getAddress();
        if (updateDTO.getAddressDefault() != null) this.addressDefault = updateDTO.getAddressDefault();
        if (updateDTO.getAddressDetail() != null) this.addressDetail = updateDTO.getAddressDetail();
    }

    // 양방향 (USerSkill 저장)
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkills = new ArrayList<>();

    public void addSkill(Skill skill) {
        UserSkill userSkill = new UserSkill(this, skill);
        userSkills.add(userSkill);  // 양방향 동기화
    }

}
