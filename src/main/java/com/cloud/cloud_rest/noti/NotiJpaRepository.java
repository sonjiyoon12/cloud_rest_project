package com.cloud.cloud_rest.noti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotiJpaRepository extends JpaRepository<Noti, Long> {

    Page<Noti> findByUserUserId(Long userId, Pageable pageable);

    List<Noti> findByUserUserId(Long userId);
}
