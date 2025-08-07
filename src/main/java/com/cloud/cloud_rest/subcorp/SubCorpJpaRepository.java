package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCorpJpaRepository extends JpaRepository <SubCorp, Long>{

    @Query("select s from SubCorp s join fetch s.user u where u.userId = :userId")
    List<SubCorp> findByUser(@Param("userId") Long userId);

    SubCorp findByUserUserIdAndCorpCorpId(Long userId, Long corpId);

    @Query("select s.user from SubCorp s join s.corp c where c.corpId = :corpId ")
    List<User> findUserByCorpId(@Param("corpId") Long corpId);
}
