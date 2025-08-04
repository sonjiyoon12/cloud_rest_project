package com.cloud.cloud_rest.corp;

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
@Table(name = "corp_tb")
@Builder
public class Corp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corpId;
    private String corpName;
    private String loginId;
    private String password;
    private String email;

    @Builder.Default
    private String corpImage = "basic.png";

    @CreationTimestamp
    private Timestamp createdAt;

    public void update(CorpRequest.UpdateDTO updateDTO, String imagePath){
        this.corpName = updateDTO.getCorpName();
        this.email = updateDTO.getEmail();
        this.corpImage = imagePath;
    }
}
