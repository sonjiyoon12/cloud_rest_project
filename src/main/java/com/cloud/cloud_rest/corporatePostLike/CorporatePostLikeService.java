package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.corporate.CorporatePostRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporatePostLikeService {

    private final CorporatePostLikeRepository corporatePostLikeRepository;
    private final CorporatePostRepository corporatePostRepository;
    private final UserRepository userRepository;

    @Transactional
    public void toggleLike(Long postId, SessionUser sessionUser) {
        // 1. 좋아요를 누를 게시물을 찾습니다.
        CorporatePost post = corporatePostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + postId));

        // 2. 좋아요 엔티티 생성을 위해 User 엔티티를 조회합니다.
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        // 3. 해당 사용자가 이미 이 게시물에 좋아요를 눌렀는지 확인합니다.
        Optional<CorporatePostLike> existingLike = corporatePostLikeRepository.findByUserAndCorporatePost(user, post);

        if (existingLike.isPresent()) {
            // 좋아요가 존재하면 -> 좋아요 취소 (엔티티 삭제)
            corporatePostLikeRepository.delete(existingLike.get());
            post.updateLikeCount(post.getLikeCount() - 1);
        } else {
            // 좋아요가 없으면 -> 좋아요 추가 (엔티티 생성 및 저장)
            CorporatePostLike newLike = CorporatePostLike.builder()
                    .user(user)
                    .corporatePost(post)
                    .build();
            corporatePostLikeRepository.save(newLike);
            post.updateLikeCount(post.getLikeCount() + 1);
        }
    }
}