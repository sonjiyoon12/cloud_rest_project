package com.cloud.cloud_rest.loginhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory,Long> {
    @Query("select lh from LoginHistory lh where lh.loginTime >= :todayStart")
    List<LoginHistory> findAllByLoginTimeAfter(@Param("todayStart") Timestamp todayStart);

}
