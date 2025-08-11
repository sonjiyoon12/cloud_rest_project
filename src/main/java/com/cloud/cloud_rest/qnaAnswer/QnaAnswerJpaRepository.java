package com.cloud.cloud_rest.qnaAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QnaAnswerJpaRepository extends JpaRepository<QnaAnswer, Long> {

    @Query("select fa from QnaAnswer fa join fetch fa.qnaBoard fb where fb.qnaBoardId = :qnaBoardId")
    Optional<QnaAnswer> findByQnaBoardId(@Param("qnaBoardId") Long qnaBoardId);
}
