package com.cloud.cloud_rest.skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    // 기술명 조회
    Skill findByName(String name);

    // 기술명 중복확인
    boolean existsByName(String name);
}
