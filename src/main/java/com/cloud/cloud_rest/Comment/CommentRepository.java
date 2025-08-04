package com.cloud.cloud_rest.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 필요한 경우 추가적인 메서드를 정의할 수 있습니다.
}
