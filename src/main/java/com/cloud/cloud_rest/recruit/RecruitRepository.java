package com.cloud.cloud_rest.recruit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    List<Recruit> findByCorpId(Long corpId);
    //Corp JPQL
    //@Query("SELECT r FROM Recruit r WHERE r.corp.corpId = :corpId")
    //List<Recruit> findByCorpEntity(@Param("corpId") Long corpId);

    // 기업 ID로 공고 수 카운트
    @Query("SELECT COUNT(r) FROM Recruit r WHERE r.corp.corpId = :corpId")
    long countByCorpId(@Param("corpId") Long corpId);

}
