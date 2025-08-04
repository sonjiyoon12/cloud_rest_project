package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_skill_tb")
@IdClass(UserSkill.class) // 복합키 클래스
public class UserSkill {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToStringExclude
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    @ToStringExclude
    private Skill skill;
}
