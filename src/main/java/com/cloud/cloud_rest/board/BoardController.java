package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.Comment.CommentResponseDto;
import com.cloud.cloud_rest.Comment.CommentService;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> savePost(@RequestBody BoardRequestDto.SaveDto saveDto,
                                      @RequestAttribute("sessionUser") SessionUser sessionUser) throws IOException {
        boardService.savePost(saveDto, sessionUser);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<BoardResponseDto.ListDto>> getPostList() {
        return ApiResponse.success(boardService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<BoardResponseDto.DetailDto> getPostDetail(@PathVariable Long id) {
        return ApiResponse.success(boardService.findById(id));
    }

    @GetMapping("/{id}/comments") // 게시글 ID에 해당하는 댓글 목록 조회 API
    public ApiResponse<List<CommentResponseDto>> getComments(@PathVariable Long id,
                                                             @RequestAttribute("sessionUser") SessionUser sessionUser) {
        // CommentService의 findCommentsByPostId 메서드 호출
        List<CommentResponseDto> comments = commentService.findCommentsByPostId(id, sessionUser);
        return ApiResponse.success(comments);
    }

    @GetMapping("/search")
    public ApiResponse<List<BoardResponseDto.ListDto>> searchPosts(@RequestBody BoardRequestDto.SearchDTO searchDTO) {
        return ApiResponse.success(boardService.search(searchDTO));
    }

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PutMapping("/{id}")
    public ApiResponse<Void> updatePost(@PathVariable Long id,
                                        @RequestBody BoardRequestDto.UpdateDto updateDto,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) throws IOException {
        boardService.updatePost(id, updateDto, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.USER, Role.ADMIN})
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        boardService.deletePost(id, sessionUser);
        return ApiResponse.success();
    }

    @GetMapping("/commented-by/{userId}")
    public ApiResponse<List<BoardResponseDto.ListDto>> getCommentedBoardsByUser(@PathVariable Long userId) {
        return ApiResponse.success(boardService.findCommentedBoardsByUser(userId));
    }
}
