package com.cloud.cloud_rest.Like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<LikeResponseDto> toggleLike(
            @Valid @RequestBody LikeRequestDto requestDto) {
        LikeResponseDto responseDto = likeService.toggleLike(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}