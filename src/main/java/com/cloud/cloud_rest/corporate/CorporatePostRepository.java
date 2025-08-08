package com.cloud.cloud_rest.corporate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CorporatePostRepository extends JpaRepository<CorporatePost, Long> {

    @Query("SELECT p FROM CorporatePost p JOIN FETCH p.author ORDER BY p.createdAt DESC")
    List<CorporatePost> findAllWithAuthorByOrderByCreatedAtDesc();
}
