package com.cloud.cloud_rest.recruit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    // [수정] 메소드 이름으로 쿼리를 자동 생성할 수 없으므로, @Query로 명시적으로 작성합니다.
    // 'r.corp.corpId'와 같이 객체 그래프를 탐색하여 정확한 필드를 지정합니다.
    // JOIN FETCH를 사용하면 연관된 Corp 엔티티를 한번의 쿼리로 함께 조회하여 성능을 최적화합니다.
    @Query("SELECT r FROM Recruit r JOIN FETCH r.corp c WHERE c.corpId = :corpId")
    List<Recruit> findByCorpId(@Param("corpId") Long corpId);

    // 기업 ID로 공고 수 카운트 (이 코드는 이미 올바르게 작성되어 있습니다)
    @Query("SELECT COUNT(r) FROM Recruit r WHERE r.corp.corpId = :corpId")
    long countByCorpId(@Param("corpId") Long corpId);
}