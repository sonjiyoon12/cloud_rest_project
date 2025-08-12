package com.cloud.cloud_rest.corporatePostLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.corporate.CorporatePostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CorporatePostLikeService {

    private final CorporatePostRepository postRepository;
    private final CorporatePostLikeRepository postLikeRepository;
    private final CorpRepository corpRepository;

    public CorporatePostLikeResponseDto togglePostLike(Long postId, SessionUser sessionUser) {
        CorporatePost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + postId));

        Corp corp = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        return postLikeRepository.findByCorpAndCorporatePost(corp, post)
                .map(this::removeLike)
                .orElseGet(() -> addLike(corp, post));
    }

    private CorporatePostLikeResponseDto addLike(Corp corp, CorporatePost post) {
        postLikeRepository.save(CorporatePostLike.builder().corp(corp).corporatePost(post).build());
        post.updateLikeCount(post.getLikeCount() + 1);
        return new CorporatePostLikeResponseDto(post.getLikeCount(), true);
    }

    private CorporatePostLikeResponseDto removeLike(CorporatePostLike like) {
        CorporatePost post = like.getCorporatePost();
        postLikeRepository.delete(like);
        post.updateLikeCount(post.getLikeCount() - 1);
        return new CorporatePostLikeResponseDto(post.getLikeCount(), false);
    }
}