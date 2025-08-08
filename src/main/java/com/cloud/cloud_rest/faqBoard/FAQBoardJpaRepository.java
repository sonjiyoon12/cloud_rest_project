package com.cloud.cloud_rest.faqBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FAQBoardJpaRepository extends JpaRepository<FAQBoard, Long> {

    // 전체조회
    @Query("select distinct f from FAQBoard f join fetch f.user")
    List<FAQBoard> findAllFQA();
}
