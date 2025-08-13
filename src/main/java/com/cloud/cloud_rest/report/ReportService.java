package com.cloud.cloud_rest.report;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.board.BoardRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ReportResponseDto createReport(ReportRequestDto.Create requestDto,Long boardId, SessionUser sessionUser) {

        // 신고 요청한 사용자 DB 에서 먼저 조회
        User reporter = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("신고한 사용자를 찾을 수 없습니다"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시판 을 찾을수없습니다"));

        // DTO를 사용하여 Report 엔티티 생성 및 저장
        Report report = requestDto.toEntity(board,reporter);
        reportRepository.save(report);

        // 저장된 엔티티 -> DTO 변환해서 반환
        return new ReportResponseDto(report);
    }
}
