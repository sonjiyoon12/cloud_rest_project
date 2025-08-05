package com.cloud.cloud_rest.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    /**
     * 스킬 이름으로 스킬을 찾는 메소드 (대소문자 구분)
     *
     * @param name 스킬 이름
     * @return Optional<Skill>
     */
    Optional<Skill> findByName(String name);

    /**
     * 스킬 이름으로 스킬을 찾는 메소드 (대소문자 무시)
     * 중복 저장을 방지하기 위해 사용됩니다.
     *
     * @param name 스킬 이름
     * @return Optional<Skill>
     */
    Optional<Skill> findByNameIgnoreCase(String name);

    /**
     * 해당 스킬이 채용공고에서 사용 중인지 확인
     *
     * @param skillId 확인할 스킬의 ID
     * @return 사용 중이면 true, 아니면 false
     */
    @Query("select count(rs) > 0 from RecruitSkill rs where rs.skill.skillId = :skillId")
    boolean isUsedInRecruitSkill(@Param("skillId") Long skillId);

    /**
     * 해당 스킬이 이력서에서 사용 중인지 확인
     *
     * @param skillId 확인할 스킬의 ID
     * @return 사용 중이면 true, 아니면 false
     */
    @Query("select count(rs) > 0 from ResumeSkill rs where rs.skill.skillId = :skillId")
    boolean isUsedInResumeSkill(@Param("skillId") Long skillId);

    /**
     * 해당 스킬이 사용자에 의해 선택되었는지 확인
     *
     * @param skillId 확인할 스킬의 ID
     * @return 사용 중이면 true, 아니면 false
     */
    @Query("select count(us) > 0 from UserSkill us where us.skill.skillId = :skillId")
    boolean isUsedInUserSkill(@Param("skillId") Long skillId);
}
