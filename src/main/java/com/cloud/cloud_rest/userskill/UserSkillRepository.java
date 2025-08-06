package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill,UserSkillId> {

    @Query("""
            select distinct us.skill.name
            from UserSkill us
            where us.user = :user
        """)
    List<String> findByUserMatch(@Param("user") User user);

}
