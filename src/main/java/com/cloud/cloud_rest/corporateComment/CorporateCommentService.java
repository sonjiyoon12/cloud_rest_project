package com.cloud.cloud_rest.corporateComment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corporate.CorporatePost;
import com.cloud.cloud_rest.corporate.CorporatePostRepository;
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
    private final CorpRepository corpRepository;

    @Transactional
    public void saveComment(Long postId, CorporateCommentRequestDto.SaveDto saveDto, SessionUser sessionUser) {
        CorporatePost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + postId));

        // SessionUser의 ID를 사용하여 Corp 엔티티를 조회
        Corp author = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("작성자를 찾을 수 없습니다: " + sessionUser.getId()));

        CorporatePostComment comment = CorporatePostComment.builder()
                .content(saveDto.getContent())
                .author(author)
                .corporatePost(post)
                .build();

        commentRepository.save(comment);
    }

    public List<CorporateCommentResponseDto.CommentDto> findCommentsByPostId(Long postId, SessionUser sessionUser) {
        List<CorporatePostComment> comments = commentRepository.findAllByPostIdWithAuthor(postId);
        return comments.stream()
                .map(comment -> new CorporateCommentResponseDto.CommentDto(comment, sessionUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long commentId, CorporateCommentRequestDto.UpdateDto updateDto, SessionUser sessionUser) {
        CorporatePostComment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        comment.update(updateDto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, SessionUser sessionUser) {
        CorporatePostComment comment = findCommentById(commentId);
        checkOwnership(comment, sessionUser);
        commentRepository.delete(comment);
    }

    private CorporatePostComment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));
    }

    private void checkOwnership(CorporatePostComment comment, SessionUser sessionUser) {
        // 댓글 작성자의 Corp ID와 세션 사용자의 ID를 비교
        if (!Objects.equals(comment.getAuthor().getCorpId(), sessionUser.getId())) {
            throw new SecurityException("해당 댓글에 대한 권한이 없습니다.");
        }
    }
}
