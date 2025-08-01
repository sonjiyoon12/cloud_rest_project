package com.cloud.cloud_rest.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.userId = :userId")
    Optional<User> findByUserId(@Param("userId") String loginId);
}
