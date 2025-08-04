package com.cloud.cloud_rest.recruitskill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitSkillRepository extends JpaRepository<RecruitSkill, RecruitSkillId> {

    boolean existsBySkillSkillId(long skillId);


}
