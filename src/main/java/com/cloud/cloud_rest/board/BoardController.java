package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> savePost(@ModelAttribute @Valid BoardRequestDto.SaveDto saveDto,
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

    @GetMapping("/search")
    public ApiResponse<List<BoardResponseDto.ListDto>> searchPosts(@ModelAttribute BoardRequestDto.SearchDTO searchDTO) {
        return ApiResponse.success(boardService.search(searchDTO));
    }

    @Auth(roles = {Role.USER, Role.ADMIN})
    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> updatePost(@PathVariable Long id,
                                        @ModelAttribute @Valid BoardRequestDto.UpdateDto updateDto,
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
}