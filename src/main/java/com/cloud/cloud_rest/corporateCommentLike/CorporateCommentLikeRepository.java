package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporateCommentLikeRepository extends JpaRepository<CorporateCommentLike, Long> {
    Optional<CorporateCommentLike> findByCorpAndComment(Corp corp, CorporatePostComment comment);
}