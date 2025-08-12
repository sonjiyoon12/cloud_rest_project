package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corporate.CorporatePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CorporatePostLikeRepository extends JpaRepository<CorporatePostLike, Long> {
    Optional<CorporatePostLike> findByCorpAndCorporatePost(Corp corp, CorporatePost post);
}