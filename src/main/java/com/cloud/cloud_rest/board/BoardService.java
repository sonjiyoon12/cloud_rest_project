package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FileUploadUtil fileUploadUtil;
    private final UploadProperties uploadProperties;
    private final Base64FileConverterUtil base64FileConverterUtil;

    // 이미지 경로를 파라미터로 받아 게시글을 저장
    @Transactional
    public Board saveBoard(BoardRequestDto.SaveDto saveDto, Long userId, String imagePath) {
        Board board = saveDto.toEntity(imagePath);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable, String search) {
        if (search != null && !search.trim().isEmpty()) {
            return boardRepository.searchByKeyword(search, pageable);
        }
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
    }

    @Transactional
    public void increaseViews(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
        board.setViews(board.getViews() + 1);
    }

    // 이미지 경로 업데이트
    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto.UpdateDto updateDto, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 수정 권한이 없습니다.");
        }

        String oldFileName = board.getImagePath();
        String savedFileName = null;

        try {
            MultipartFile multipartFile = null;

            // 웹 (MultipartFile) 용
            if (updateDto.getImagePath() != null && !updateDto.getImagePath().isEmpty()) {
                multipartFile = updateDto.getImagePath();
            }

            // 앱(Base64) 용
            else if (updateDto.getImagePathBase64() != null && !updateDto.getImagePathBase64().isBlank()) {
                multipartFile = Base64FileConverterUtil.convert(updateDto.getImagePathBase64());
            }

            // 해당 파일이 없으면 savedFileName에 저장
            if (multipartFile != null) {
                savedFileName = fileUploadUtil.uploadProfileImage(multipartFile, uploadProperties.getCorpDir());
            }

            // 이전 이미지 삭제
            if (savedFileName != null && oldFileName != null) {
                fileUploadUtil.deleteProfileImage(oldFileName);
            }

            // 해당 파일을 저장
            board.update(updateDto, savedFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        return board;
    }

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

    // 게시글별 댓글 목록을 페이징하여 조회
    @Transactional(readOnly = true)
    public Page<Comment> getCommentsByBoardId(Long boardId, Pageable pageable) {

        return commentRepository.findByBoardBoardId(boardId, pageable);


    }

    // 특정 사용자가 댓글을 작성한 모든 게시글을 조회
    @Transactional(readOnly = true)
    public Page<Board> getBoardsCommentedByUser(Long userId, Pageable pageable) {
        return boardRepository.findBoardsCommentedByUser(userId, pageable);
    }
}
