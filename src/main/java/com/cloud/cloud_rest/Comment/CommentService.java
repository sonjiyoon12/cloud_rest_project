package com.cloud.cloud_rest.Comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    // 댓글 수정 메서드
    public void updateComment(CommentRequestDto requestDto) {
        System.out.println("댓글 ID" + requestDto.getCommentId() + "를 수정 했습니다");
        System.out.println("새로운 내용 : " + requestDto.getContent());
    }

    // 댓글 삭제 메서드
    public void deleteComment(CommentRequestDto requestDto) {
        System.out.println("댓글 ID" + requestDto.getCommentId() + "를 삭제 했습니다");
    }

    // 댓글 등록 메서드
    public void writeComment(CommentRequestDto requestDto, boolean isSecret) {
        System.out.println("댓글 ID" + requestDto.getCommentId() + "등록 되었습니다");
    }
}
