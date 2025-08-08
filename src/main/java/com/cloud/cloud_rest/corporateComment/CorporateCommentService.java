package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.corporate.CorporatePostRepository;
import com.cloud.cloud_rest.corporateCommentLike.CorporateCommentLikeRepository;
import com.cloud.cloud_rest.corporateCommentLike.CorporateCommentLike;
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
public class CorporateCommentService {

    private final CorporateCommentRepository commentRepository;
    private final CorporatePostRepository postRepository;
    private final CorporateCommentLikeRepository commentLikeRepository;

    @Transactional
    public void saveComment(Long postId, CorporateCommentRequestDto.SaveDto saveDto, User sessionUser) {
        CorporatePost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + postId));

        CorporatePostComment comment = CorporatePostComment.builder()
                .content(saveDto.getContent())
                .author(sessionUser)
                .corporatePost(post)
                .build();

        commentRepository.save(comment);
    }

    public List<CorporateCommentResponseDto.CommentDto> findCommentsByPostId(Long postId, User sessionUser) {
        List<CorporatePostComment> comments = commentRepository.findAllByPostIdWithAuthor(postId);
        return comments.stream()
                .map(comment -> new CorporateCommentResponseDto.CommentDto(comment, sessionUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long commentId, CorporateCommentRequestDto.UpdateDto updateDto, User sessionUser) {
        CorporatePostComment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        comment.update(updateDto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, User sessionUser) {
        CorporatePostComment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        commentRepository.delete(comment);
    }

    @Transactional
    public void toggleCommentLike(Long commentId, User sessionUser) {
        CorporatePostComment comment = findCommentById(commentId);

        commentLikeRepository.findByUserAndComment(sessionUser, comment).ifPresentOrElse(
                // 좋아요가 존재하면
                like -> {
                    commentLikeRepository.delete(like);
                    comment.updateLikeCount(comment.getLikeCount() - 1);
                },
                // 좋아요가 없으면
                () -> {
                    CorporateCommentLike newLike = CorporateCommentLike.builder().user(sessionUser).comment(comment).build();
                    commentLikeRepository.save(newLike);
                    comment.updateLikeCount(comment.getLikeCount() + 1);
                }
        );
    }

    private CorporatePostComment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));
    }

    private void checkOwnership(CorporatePostComment comment, User sessionUser) {
        if (!Objects.equals(comment.getAuthor().getUserId(), sessionUser.getUserId())) {
            throw new SecurityException("해당 댓글에 대한 권한이 없습니다.");
        }
    }
}