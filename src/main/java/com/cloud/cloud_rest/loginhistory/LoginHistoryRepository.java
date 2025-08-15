package com.cloud.cloud_rest.loginhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory,Long> {
    @Query("select lh from LoginHistory lh where lh.loginTime >= :todayStart")
    List<LoginHistory> findAllByLoginTimeAfter(@Param("todayStart") Timestamp todayStart);

    @Query("SELECT lh FROM LoginHistory lh WHERE lh.user.userId = :userId AND lh.isActive = true ORDER BY lh.loginTime DESC")
    Optional<LoginHistory> findTopByUserId(@Param("userId") Long userId);

    @Query("SELECT lh FROM LoginHistory lh WHERE lh.corp.corpId = :corpId AND lh.isActive = true ORDER BY lh.loginTime DESC")
    Optional<LoginHistory> findTopByCorpId(@Param("corpId") Long corpId);

    @Query("SELECT lh FROM LoginHistory lh WHERE lh.user.userId = :adminId AND lh.isActive = true ORDER BY lh.loginTime DESC")
    Optional<LoginHistory> findTopByAdminId(@Param("adminId") Long adminId);

    @Modifying
    @Query("UPDATE LoginHistory lh SET lh.isActive = false WHERE lh.corp.corpId = :corpId AND lh.isActive = true")
    void deactivateAllActiveByCorpId(@Param("corpId") Long corpId);

    @Modifying
    @Query("UPDATE LoginHistory lh SET lh.isActive = false WHERE lh.user.userId = :userId AND lh.isActive = true")
    void deactivateAllActiveByUserId(@Param("userId") Long userId);

}
