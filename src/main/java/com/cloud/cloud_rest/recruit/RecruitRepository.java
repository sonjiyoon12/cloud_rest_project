package com.cloud.cloud_rest.recruit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    // 유료공고 우선 조회 (페이징)
    @Query("SELECT r FROM Recruit r LEFT JOIN r.recruitPaid rp ORDER BY CASE WHEN rp.id IS NOT NULL THEN 0 ELSE 1 END ASC, r.createdAt DESC")
    Page<Recruit> findAllWithPaidPriority(Pageable pageable);

    // 기업 ID로 공고 조회 (N+1 방지)
    @Query("SELECT r FROM Recruit r JOIN FETCH r.corp c WHERE c.corpId = :corpId")
    List<Recruit> findByCorpId(@Param("corpId") Long corpId);

    // 기업 ID로 공고 수 조회
    @Query("SELECT COUNT(r) FROM Recruit r WHERE r.corp.corpId = :corpId")
    long countByCorpId(@Param("corpId") Long corpId);

    // 공고 ID와 기업 ID로 공고 조회 (소유권 확인)
    @Query("SELECT r FROM Recruit r WHERE r.recruitId = :recruitId AND r.corp.corpId = :corpId")
    Optional<Recruit> findByIdAndCorpId(@Param("recruitId") Long recruitId, @Param("corpId") Long corpId);

    // 기술 스택으로 공고 조회
    @Query("""
                SELECT DISTINCT r
                FROM Recruit r
                JOIN r.recruitSkills cs
                WHERE cs.skill.name IN :skillNames
            """)
    List<Recruit> findByMatchingSkills(@Param("skillNames") List<String> skillNames);
}
