package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporateCommentLikeRepository extends JpaRepository<CorporateCommentLike, Long> {
    Optional<CorporateCommentLike> findByUserAndComment(User user, CorporatePostComment comment);
}