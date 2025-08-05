package com.cloud.cloud_rest.career;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerJpaRepository extends JpaRepository<Career, Long> {
    List<Career> findByResume_ResumeId(Long resumeId);
}
