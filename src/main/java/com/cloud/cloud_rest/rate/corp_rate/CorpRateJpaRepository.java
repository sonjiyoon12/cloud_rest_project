package com.cloud.cloud_rest.rate.corp_rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CorpRateJpaRepository extends JpaRepository<CorpRate, Long> {

    CorpRate findByUserUserIdAndCorpCorpId(@Param("userId") Long userId,
                                           @Param("corpId") Long corpId);

    Optional<List<CorpRate>> findByUserUserId(@Param("userId") Long userId);
}
