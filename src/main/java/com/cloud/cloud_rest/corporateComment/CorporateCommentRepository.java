package com.cloud.cloud_rest.corporateComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorporateCommentRepository extends JpaRepository<CorporatePostComment, Long> {

    @Query("SELECT c FROM CorporatePostComment c JOIN FETCH c.author WHERE c.corporatePost.id = :postId ORDER BY c.createdAt ASC")
    List<CorporatePostComment> findAllByPostIdWithAuthor(@Param("postId") Long postId);
}
