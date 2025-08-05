package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final FileUploadUtil fileUploadUtil;
    private final Base64FileConverterUtil base64FileConverterUtil;

    // 이미지 업로드 API (직접 MultipartFile을 받는 경우)
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String savedImagePath = fileUploadUtil.uploadProfileImage(file, "board-images");
            return new ResponseEntity<>(savedImagePath, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("이미지 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 작성 (Base64 이미지 문자열을 포함하는 DTO를 받는 경우)
    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody BoardRequestDto.SaveDto saveDto) {
        try {
            Long userId = saveDto.getUserId();
            // userId가 유효한지 확인
            if (userId == null) {
                throw new IllegalArgumentException("userId가 누락되었습니다.");
            }
            String base64Image = saveDto.getBase64Image();
            String savedImagePath = null;
            if (base64Image != null && !base64Image.isEmpty()) {
                MultipartFile convertedFile = base64FileConverterUtil.convert(base64Image);
                savedImagePath = fileUploadUtil.uploadProfileImage(convertedFile, "board-images");
            }
            Board savedBoard = boardService.saveBoard(saveDto, userId, savedImagePath);
            return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>("이미지 업로드 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 게시글 목록 조회 (페이징, 정렬, 검색 포함)
    @GetMapping
    public ResponseEntity<Page<Board>> getBoardList(
            // 기본 값 최신순
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String search) {

        Page<Board> boardPage = boardService.getBoardList(pageable, search);
        return new ResponseEntity<>(boardPage, HttpStatus.OK);
    }

    // 특정 사용자가 댓글을 작성한 모든 게시글을 조회
    @GetMapping("/users/{userId}/commented-boards")
    public ResponseEntity<Page<Board>> getBoardsCommentedByUser(
            @RequestParam Long userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> commentedBoards = boardService.getBoardsCommentedByUser(userId, pageable);
        return new ResponseEntity<>(commentedBoards, HttpStatus.OK);
    }

    // 특정 게시글 조회 (조회수 증가 포함)
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        try {
            // 조회수 증가 로직을 별도로 호출
            boardService.increaseViews(boardId);
            // 게시글 조회 로직 호출
            Board board = boardService.getBoard(boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                         @RequestBody BoardRequestDto.UpdateDto updateDto,
                                         @RequestParam Long userId) {
        try {
            Board updatedBoard = boardService.updateBoard(boardId, updateDto, userId);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
                                         @RequestParam Long userId) {
        try {
            boardService.deleteBoard(boardId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
