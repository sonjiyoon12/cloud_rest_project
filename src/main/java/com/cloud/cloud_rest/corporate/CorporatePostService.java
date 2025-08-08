package com.cloud.cloud_rest.corporate;

import com.cloud.cloud_rest.corporatePostLike.CorporatePostLike;
import com.cloud.cloud_rest.corporatePostLike.CorporatePostLikeRepository;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporatePostService {

    private final CorporatePostRepository corporatePostRepository;
    private final CorporatePostLikeRepository corporatePostLikeRepository;


    @Transactional
    public void savePost(CorporatePostRequestDto.SaveDto saveDTO, User sessionUser) {
        CorporatePost post = CorporatePost.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .author(sessionUser)
                .build();
        corporatePostRepository.save(post);
    }

    public List<CorporatePostResponseDto.ListDto> findAll() {
        return corporatePostRepository.findAllWithAuthorByOrderByCreatedAtDesc().stream()

                .map(CorporatePostResponseDto.ListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CorporatePostResponseDto.DetailDto findById(Long id) {
        CorporatePost post = findByPostById(id);
        post.increaseViewCount();
        return new CorporatePostResponseDto.DetailDto(post);
    }

    @Transactional
    public void updatePost(Long id, CorporatePostRequestDto.UpdateDto updateDTO, User sessionUser) {
        CorporatePost post = findByPostById(id);
        checkOwnership(post, sessionUser);
        post.update(updateDTO.getTitle(), updateDTO.getContent());
    }

    @Transactional
    public void deletePost(Long id, User sessionUser) {
        CorporatePost post = findByPostById(id);
        checkOwnership(post, sessionUser);
        corporatePostRepository.delete(post);
    }

    /**
     * 게시물 좋아요/좋아요 취소
     */
    @Transactional
    public void togglePostLike(Long postId, User sessionUser) {
        CorporatePost post = findByPostById(postId);
        corporatePostLikeRepository.findByUserAndCorporatePost(sessionUser, post).ifPresentOrElse(
                // 좋아요가 존재하면
                like -> {
                    corporatePostLikeRepository.delete(like);
                    post.updateLikeCount(post.getLikeCount() - 1);
                },
                // 좋아요가 없으면
                () -> {
                    CorporatePostLike newLike = CorporatePostLike.builder().user(sessionUser).corporatePost(post).build();
                    corporatePostLikeRepository.save(newLike);
                    post.updateLikeCount(post.getLikeCount() + 1);
                }
        );
    }

    /**
     * 게시물 조회
     */
    private CorporatePost findByPostById(Long id) {
        return corporatePostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + id));
    }

    /**
     * 게시물 소유권 확인
     */
    private void checkOwnership(CorporatePost post, User sessionUser) {
        // getUserId()를 사용하여 사용자 ID 비교
        if (!Objects.equals(post.getAuthor().getUserId(), sessionUser.getUserId())) {
            throw new SecurityException("해당 게시물에 대한 권한이 없습니다");
        }
    }
}