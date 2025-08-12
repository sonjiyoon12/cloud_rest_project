package com.cloud.cloud_rest.corporate.corporate_tag;

import com.cloud.cloud_rest.corporate.CorporatePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CorporateTagRepository extends JpaRepository<CorporateTag, Long> {
    Optional<CorporateTag> findByName(String name);
    List<CorporateTag> findByCorporatePost(CorporatePost corporatePost);
    void deleteByCorporatePost(CorporatePost corporatePost);
}