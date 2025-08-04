package com.cloud.cloud_rest.corp;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CorpRepository extends JpaRepository<Corp, Long> {


    @Query("select c from Corp c where c.loginId = :loginId")
    Optional<Corp> findByLoginId(@Param("loginId") String loginId);

    @Query("select c from Corp c where c.loginId = :loginId")
    boolean existsLoginId(@Param("loginId") String loginId);


}
