package com.cloud.cloud_rest.qnaBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnaBoardJpaRepository extends JpaRepository<QnaBoard, Long> {

    // 전체조회
    @Query("select distinct f from QnaBoard f join fetch f.user")
    List<QnaBoard> findAllQna();

    // 상세보기
    @Query("select f from QnaBoard f join fetch f.user u left join fetch f.qnaAnswer fa where f.qnaBoardId = :qnaBoardId")
    Optional<QnaBoard> findByIdWithDetail(@Param("qnaBoardId") Long qnaBoardId);

}
