package com.cloud.cloud_rest.subcorp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCorpJpaRepository extends JpaRepository <SubCorp, Long>{

    @Query("select s from SubCorp s join fetch s.user u where u.id = :userId")
    List<SubCorp> findByUser(@Param("userId") Long userId);

    SubCorp findByUserUserIdAndCorpCorpId(Long userId, Long corpId);
}
