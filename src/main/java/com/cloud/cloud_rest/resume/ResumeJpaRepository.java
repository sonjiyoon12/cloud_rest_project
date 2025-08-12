package com.cloud.cloud_rest.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeJpaRepository extends JpaRepository<Resume, Long> {

    // 이력서 전체조회
    @Query("select distinct r from Resume r join fetch r.user left join fetch r.resumeSkills rs left join fetch rs.skill left join fetch r.careers")
    List<Resume> findAllResumeAndSkillsAndCareers();


    // 이력서 상세보기
    @Query("select distinct r from Resume r join fetch r.user left join fetch r.resumeSkills rs left join fetch rs.skill left join fetch r.careers where r.resumeId = :resumeId")
    Optional<Resume> findByIdWithDetail(@Param("resumeId") Long resumeId);
}
