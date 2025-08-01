package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._define.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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
    private String userImage = "basic.png";
    private String address;
    private String addressDefault;
    private String addressDetail;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getFormatTime(){
        return DateUtil.timestampFormat(createdAt);
    }


    // update Dirty Checking
    public void update(UserRequest.UpdateDTO updateDTO,String userUploadImage){
        this.username = updateDTO.getUsername();
        this.phoneNumber = updateDTO.getPhoneNumber();
        this.userImage = userUploadImage;
        this.address = updateDTO.getAddress();
        this.addressDefault = updateDTO.getAddressDefault();
        this.addressDetail = updateDTO.getAddressDetail();
    }
}
