package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest.Comment.CommentResponseDto;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import com.cloud.cloud_rest.board.board_tag.BoardTagRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FileUploadUtil fileUploadUtil;
    private final UploadProperties uploadProperties;
    private final Base64FileConverterUtil base64FileConverterUtil;
    private final UserRepository userRepository;
    private final BoardTagRepository boardTagRepository;


    // 게시글을 저장하고 DTO를 반환
    @Transactional
    public BoardResponseDto.DetailDto saveBoard(BoardRequestDto.SaveDto saveDto) throws IOException {
        String savedImagePath = null;
        String base64Image = saveDto.getBase64Image();

        if (base64Image != null && !base64Image.isBlank()) {
            MultipartFile convertedFile = base64FileConverterUtil.convert(base64Image);
            savedImagePath = fileUploadUtil.uploadProfileImage(convertedFile, uploadProperties.getCorpDir());
        }

        User user = userRepository.findById(saveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID 입니다: " + saveDto.getUserId()));

        Board board = saveDto.toEntity(user, savedImagePath);
        Board savedBoard = boardRepository.save(board);

        if (saveDto.getBoardTags() != null) {
            saveDto.getBoardTags().forEach(
                    tag -> {
                        BoardTag boardTag = BoardTag
                                .builder()
                                .board(board)
                                .tagName(tag)
                                .build();
                        boardTagRepository.save(boardTag);
                    }
            );
                }

        return new BoardResponseDto.DetailDto(savedBoard);
    }

    // 게시글 목록을 페이징하여 DTO로 반환
    @Transactional(readOnly = true)
    public Page<BoardResponseDto.ListDto> getBoardList(Pageable pageable, BoardRequestDto.SearchDTO searchDTO) {
        // 이제 서비스는 그냥 Repository에 그대로 전달만 해주면 됨!
        Page<Board> boardPage = boardRepository.findBySearchOption(pageable, searchDTO);
        return boardPage.map(BoardResponseDto.ListDto::new);
    }

    // 특정 게시글을 조회하고 조회수를 증가시킨 후 DTO를 반환
    @Transactional
    public BoardResponseDto.DetailDto getBoardWithIncreaseViews(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));
        board.setViews(board.getViews() + 1);


        return new BoardResponseDto.DetailDto(board);
    }


    // Base64 이미지를 포함한 DTO로 게시글을 수정하고 수정된 DTO를 반환
    @Transactional
    public BoardResponseDto.UpdateDto updateBoard(Long boardId, BoardRequestDto.UpdateDto updateDto) throws IOException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUser().getUserId().equals(updateDto.getUserId())) {
            throw new IllegalArgumentException("게시물 수정 권한이 없습니다.");
        }

        String oldImagePath = board.getImagePath();
        String savedImagePath = oldImagePath;
        String base64Image = updateDto.getImagePathBase64();

        if (base64Image != null && !base64Image.isBlank()) {
            if (oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }
            MultipartFile convertedFile = base64FileConverterUtil.convert(base64Image);
            savedImagePath = fileUploadUtil.uploadProfileImage(convertedFile, uploadProperties.getBoardDir());
        } else if ((base64Image == null || base64Image.isBlank()) && oldImagePath != null) {
            fileUploadUtil.deleteProfileImage(oldImagePath);
            savedImagePath = null;
        }

        board.update(updateDto, savedImagePath);
        return new BoardResponseDto.UpdateDto(board);
    }

    // 게시글을 삭제
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다: " + boardId));

        if (!board.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
        }

        if (board.getImagePath() != null) {
            fileUploadUtil.deleteProfileImage(board.getImagePath());
        }
        boardRepository.delete(board);
    }

    // 게시글별 댓글 목록을 페이징하여 조회
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsByBoardId(Long boardId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findByBoardBoardId(boardId, pageable);

        List<CommentResponseDto> commentDtoList = commentsPage.getContent().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(commentDtoList, pageable, commentsPage.getTotalElements());
    }

    // 특정 사용자가 댓글을 작성한 모든 게시글을 조회
    @Transactional(readOnly = true)
    public Page<Board> getBoardsCommentedByUser(Long userId, Pageable pageable) {
        return boardRepository.findBoardsCommentedByUser(userId, pageable);
    }
}