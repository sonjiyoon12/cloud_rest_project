package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corporateComment.CorporateCommentRepository;
import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CorporateCommentLikeService {

    private final CorporateCommentRepository commentRepository;
    private final CorporateCommentLikeRepository commentLikeRepository;
    private final CorpRepository corpRepository;

    public CorporateCommentLikeResponseDto toggleCommentLike(Long commentId, SessionUser sessionUser) {
        CorporatePostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));

        // SessionUser의 ID를 사용하여 실제 Corp 엔티티를 조회
        Corp corp = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        return commentLikeRepository.findByCorpAndComment(corp, comment)
                .map(this::removeLike) // 좋아요가 존재하면 removeLike 실행
                .orElseGet(() -> addLike(corp, comment)); // 좋아요가 없으면 addLike 실행
    }

    // 좋아요를 추가하고, 변경된 상태를 DTO로 반환하는 보조 메서드
    private CorporateCommentLikeResponseDto addLike(Corp corp, CorporatePostComment comment) {
        CorporateCommentLike newLike = CorporateCommentLike.builder()
                .corp(corp)
                .comment(comment)
                .build();
        commentLikeRepository.save(newLike);
        comment.updateLikeCount(comment.getLikeCount() + 1);
        return new CorporateCommentLikeResponseDto(comment.getLikeCount(), true);
    }

    // 좋아요를 삭제하고, 변경된 상태를 DTO로 반환하는 보조 메서드
    private CorporateCommentLikeResponseDto removeLike(CorporateCommentLike like) {
        // 좋아요를 삭제하기 전에, 좋아요에 연결된 댓글 엔티티를 가져옴
        CorporatePostComment comment = like.getComment();
        commentLikeRepository.delete(like);
        comment.updateLikeCount(comment.getLikeCount() - 1);
        return new CorporateCommentLikeResponseDto(comment.getLikeCount(), false);
    }
}