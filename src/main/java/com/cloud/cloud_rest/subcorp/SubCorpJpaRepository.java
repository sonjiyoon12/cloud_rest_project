package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubCorpJpaRepository extends JpaRepository <SubCorp ,Long>{

    @Query("select s from SubCorp s join fetch s.user u where u.id = :userId")
    List<SubCorp> findByUser(@Param("id") Long userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM SubCorp s WHERE s.user = :user AND s.corp = :corp")
    Optional<SubCorp> existsByUserAndCompany(@Param("user") User user, @Param("corp") Corp corp);

    @Transactional
    void deleteByUserIdAndCorpId(Long userId, Long corpId);

}
