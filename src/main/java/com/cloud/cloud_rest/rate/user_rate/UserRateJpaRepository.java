package com.cloud.cloud_rest.rate.user_rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRateJpaRepository extends JpaRepository<UserRate, Long> {

    UserRate findByUserUserIdAndCorpCorpId(@Param("userId") Long userId,
                                           @Param("corpId") Long corpId);

    Optional<List<UserRate>> findByCorpCorpId(@Param("corpId") Long corpId);
}
