package com.cloud.cloud_rest.corporate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.response.ApiResponse;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporate-posts")
public class CorporatePostController {

    private final CorporatePostService corporatePostService;

    @Auth(roles = {Role.CORP})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> savePost(@Valid @RequestBody CorporatePostRequestDto.SaveDto saveDTO,
                                      @RequestAttribute("sessionUser") SessionUser sessionUser) {
        corporatePostService.savePost(saveDTO, sessionUser);
        return ApiResponse.success();
    }



    @GetMapping
    public ApiResponse<List<CorporatePostResponseDto.ListDto>> getPostList() {
        List<CorporatePostResponseDto.ListDto> postList = corporatePostService.findAll();
        return ApiResponse.success(postList);
    }

    @GetMapping("/{id}")
    public ApiResponse<CorporatePostResponseDto.DetailDto> getPostDetail(@PathVariable Long id) {
        CorporatePostResponseDto.DetailDto postDetail = corporatePostService.findById(id);
        return ApiResponse.success(postDetail);
    }

    @GetMapping("/search")
    public ApiResponse<List<CorporatePostResponseDto.ListDto>> searchPosts(
            @ModelAttribute CorporatePostRequestDto.SearchDTO searchDTO) {
        List<CorporatePostResponseDto.ListDto> postList = corporatePostService.searchPosts(searchDTO);
        return ApiResponse.success(postList);
    }

    @Auth(roles = {Role.CORP})
    @PutMapping("/{id}")
    public ApiResponse<Void> updatePost(@PathVariable Long id,
                                        @Valid @RequestBody CorporatePostRequestDto.UpdateDto updateDTO,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) {

        corporatePostService.updatePost(id, updateDTO, sessionUser);
        return ApiResponse.success();
    }

    @Auth(roles = {Role.CORP})
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) { // User -> SessionUser로 수정
        corporatePostService.deletePost(id, sessionUser);
        return ApiResponse.success();
    }
}