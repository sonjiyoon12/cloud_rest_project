package com.cloud.cloud_rest.corporate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorporatePostRepository extends JpaRepository<CorporatePost, Long> {

    // N+1 문제를 해결 ---> author와 tags를 fetch join
    @Query("SELECT p FROM CorporatePost p JOIN FETCH p.author LEFT JOIN FETCH p.tags ORDER BY p.createdAt DESC")
    List<CorporatePost> findAllWithAuthorByOrderByCreatedAtDesc();

    // 태그 및 키워드 검색
    @Query("SELECT DISTINCT p FROM CorporatePost p " +
            "LEFT JOIN FETCH p.author " +
            "LEFT JOIN FETCH p.tags t " +
            "WHERE (:keyword IS NULL OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
            "AND (:tags IS NULL OR t.name IN :tags) " +
            "ORDER BY p.createdAt DESC")
    List<CorporatePost> searchPosts(@Param("keyword") String keyword, @Param("tags") List<String> tags);
}
