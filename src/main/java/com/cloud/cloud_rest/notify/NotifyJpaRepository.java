package com.cloud.cloud_rest.notify;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifyJpaRepository extends JpaRepository<Notify, Long> {

    Page<Notify> findByUserUserId(Long userId, Pageable pageable);

    List<Notify> findByUserUserId(Long userId);
}
