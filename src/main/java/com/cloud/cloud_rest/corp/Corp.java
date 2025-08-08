package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest._global.utils.DateUtil;
import com.cloud.cloud_rest.corpskill.CorpSkill;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.user.Role;
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.CORP;

    public void update(CorpRequest.UpdateDTO updateDTO, String imagePath) {
        if (updateDTO.getCorpName() != null) {
            this.corpName = updateDTO.getCorpName();
        }
        if (updateDTO.getEmail() != null) {
            this.email = updateDTO.getEmail();
        }
        if (imagePath != null) {
            this.corpImage = imagePath;
        }
    }
    // JPA에서 연관된 자식 엔티티를 자동으로 삭제하는 옵션
    @Builder.Default
    @OneToMany(mappedBy = "corp",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CorpSkill> corpSkills = new ArrayList<>();

    public void addSkill(Skill skill){
        CorpSkill corpSkill = new CorpSkill(this,skill);
        corpSkills.add(corpSkill);
    }

    public String getTime(){
        return DateUtil.timestampFormat(createdAt);
    }
}
