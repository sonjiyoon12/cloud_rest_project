package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    // JOIN FETCH를 사용해 연관된 Corp 엔티티를 한번의 쿼리로 함께 조회
    @Query("SELECT r FROM Recruit r JOIN FETCH r.corp c WHERE c.corpId = :corpId")
    List<Recruit> findByCorpId(@Param("corpId") Long corpId);

    // 기업 ID로 공고 수 카운트 (이 코드는 이미 올바르게 작성되어 있습니다)
    @Query("SELECT COUNT(r) FROM Recruit r WHERE r.corp.corpId = :corpId")
    long countByCorpId(@Param("corpId") Long corpId);

    // [수정] 채용공고 ID와 기업 ID로 공고를 찾는 메소드 (r.id -> r.recruitId)
    @Query("SELECT r FROM Recruit r WHERE r.recruitId = :recruitId AND r.corp.corpId = :corpId")
    Optional<Recruit> findByIdAndCorpId(@Param("recruitId") Long recruitId, @Param("corpId") Long corpId);

    @Query("""
        select distinct r
        from Recruit r
        join r.recruitSkills cs
        where cs.skill.name in :skillNames
    """)
    List<Recruit> findByMatchingSkills(@Param("skillNames") List<String> skillNames);
}
