package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import com.cloud.cloud_rest.board.board_tag.BoardTagRepository;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardTagRepository boardTagRepository;
    private final FileUploadUtil fileUploadUtil;
    private final UploadProperties uploadProperties;
    private final CommentRepository commentRepository;

    @Transactional
    public void savePost(BoardRequestDto.SaveDto saveDto, SessionUser sessionUser) throws IOException {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        String imagePath = null;
        if (saveDto.getImage() != null && !saveDto.getImage().isEmpty()) {
            imagePath = fileUploadUtil.uploadProfileImage(saveDto.getImage(), uploadProperties.getBoardDir());
        }

        Board board = Board.builder()
                .title(saveDto.getTitle())
                .content(saveDto.getContent())
                .user(user)
                .imagePath(imagePath)
                .views(0)
                .likeCount(0)
                .build();

        saveTags(board, saveDto.getTags());
        boardRepository.save(board);
    }

    public List<BoardResponseDto.ListDto> findAll() {
        return boardRepository.findAllWithUserAndTags().stream()
                .map(BoardResponseDto.ListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponseDto.DetailDto findById(Long id) {
        Board board = boardRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        board.increaseViewCount();
        return new BoardResponseDto.DetailDto(board);
    }

    @Transactional
    public void updatePost(Long id, BoardRequestDto.UpdateDto updateDto, SessionUser sessionUser) throws IOException {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        checkOwnership(board, sessionUser);

        String imagePath = board.getImagePath();
        if (updateDto.getImage() != null && !updateDto.getImage().isEmpty()) {
            imagePath = fileUploadUtil.uploadProfileImage(updateDto.getImage(), uploadProperties.getBoardDir());
        }

        board.update(updateDto, imagePath);
        updateTags(board, updateDto.getTags());
    }

    @Transactional
    public void deletePost(Long id, SessionUser sessionUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        checkOwnership(board, sessionUser);
        boardRepository.delete(board);
    }

    public List<BoardResponseDto.ListDto> search(BoardRequestDto.SearchDTO searchDTO) {
        return boardRepository.search(searchDTO.getKeyword(), searchDTO.getTags()).stream()
                .map(BoardResponseDto.ListDto::new)
                .collect(Collectors.toList());
    }

    private void checkOwnership(Board board, SessionUser sessionUser) {
        // 게시물 작성자이거나 관리자(Role.ADMIN)인 경우에만 통과
        if (!Objects.equals(board.getUser().getUserId(), sessionUser.getId()) &&
                !Objects.equals(sessionUser.getRole(), Role.ADMIN)) {
            throw new SecurityException("해당 게시물에 대한 권한이 없습니다.");
        }
    }

    private void saveTags(Board board, List<String> tagNames) {
        if (tagNames == null) return;
        tagNames.forEach(name -> board.addTag(BoardTag.builder().name(name).board(board).build()));
    }

    private void updateTags(Board board, List<String> tagNames) {
        board.clearTags();
        saveTags(board, tagNames);
    }
    public List<BoardResponseDto.ListDto> findCommentedBoardsByUser(Long userId) {

        List<Comment> comments = commentRepository.findByUser_UserId(userId);


        Set<Long> boardIds = comments.stream()
                .map(comment -> comment.getBoard().getBoardId())
                .collect(Collectors.toSet());


        List<Board> boards = boardRepository.findAllById(boardIds);


        return BoardResponseDto.ListDto.toDtoList(boards);
    }
}