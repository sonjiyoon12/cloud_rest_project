package com.cloud.cloud_rest.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    // 댓글 등록
//    @PostMapping
//    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto.CreateDto createDto,
//                                                            @RequestParam(defaultValue = "false") boolean isPrivate) {
//        Long userId = 1L;
//        CommentResponseDto responseDto = commentService.writeComment(createDto, userId, isPrivate);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
//    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequestDto requestDto) {
        requestDto.setCommentId(commentId);
        commentService.updateComment(requestDto);
        return ResponseEntity.ok("댓글이 성공적으로 수정 되었습니다");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequestDto requestDto) {
        requestDto.setCommentId(commentId);
        commentService.deleteComment(requestDto);
        return ResponseEntity.ok("댓글이 성공적으로 삭제 되었습니다");
    }

    // 비밀 댓글
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto,
                                           @RequestParam(defaultValue = "false") boolean isPrivate) {
        System.out.println("새로운 댓글을 등록 합니다");
        System.out.println("내용 : " + requestDto.getContent());
        System.out.println("비밀 댓글 여부 : " + isPrivate);
        return ResponseEntity.status(201).body("댓글이 성공적으로 등록 되었습니다");
    }

}
