package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporatePostLikeRepository extends JpaRepository<CorporatePostLike, Long> {
    Optional<CorporatePostLike> findByUserAndCorporatePost(User user, CorporatePost post);
}
