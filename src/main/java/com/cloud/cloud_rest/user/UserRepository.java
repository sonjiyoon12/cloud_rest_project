package com.cloud.cloud_rest.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.loginId = :loginId")
    Optional<User> findByUserId(@Param("loginId") String loginId);

    @Query("select count(c) > 0 from User c where c.loginId = :loginId")
    boolean existsLoginId(@Param("loginId") String loginId);

    @Query("""
        select distinct u
        from User u
        join u.userSkills cs
        where cs.skill.name in :skillNames
    """)
    List<User> findByMatchingSkills(@Param("skillNames") List<String> skillNames);

    @Query("select u from User u where u.role = :role")
    List<User> findAllByRole(@Param("role") Role role);

    // 오늘 로그인한 유저들의 목록
    @Query("SELECT u FROM User u WHERE u.createdAt >= :todayStart")
    List<User> findAllByCreatedAtAfter(@Param("todayStart") Timestamp todayStart);

}
