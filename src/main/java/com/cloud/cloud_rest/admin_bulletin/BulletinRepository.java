package com.cloud.cloud_rest.admin_bulletin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
}
