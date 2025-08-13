package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest.corp.CorpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CorpApprovalRepository extends JpaRepository<CorpApproval,Long> {

    @Query("select ca from CorpApproval ca where ca.corp.corpId = :corpId and ca.corpStatus = :corpStatus order by ca.createdAt desc")
    Optional<CorpApproval> findTopByCorpIdAndCorpStatusOrderByCreatedAtDesc(@Param("corpId") Long corpId, @Param("corpStatus") CorpStatus corpStatus);

}
