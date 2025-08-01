package com.cloud.cloud_rest.resume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResumeJpaRepository extends JpaRepository<Resume, Long> {


    // 이력서 목록
    @Query("SELECT r FROM Resume r JOIN FETCH r.user u ORDER BY r.resumeId DESC")
    Page<Resume> findByAllUser(Pageable pageable);

    // 게시글 상세보기
    @Query("SELECT r FROM Resume r JOIN FETCH r.user u WHERE r.resumeId = :resumeId")
    Optional<Resume> findByIdUser(@Param("resumeId") Long resumeId);


}
