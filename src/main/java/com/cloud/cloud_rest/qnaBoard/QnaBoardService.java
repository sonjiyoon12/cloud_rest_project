package com.cloud.cloud_rest.qnaBoard;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.qnaAnswer.QnaAnswerJpaRepository;
import com.cloud.cloud_rest.qnaAnswer.QnaAnswer;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QnaBoardService {

    private final QnaBoardJpaRepository qnaBoardJpaRepository;
    private final QnaAnswerJpaRepository qnaAnswerJpaRepository;
    private final UserService userService;

    // 전체 조회
    public List<QnaBoardResponse.ListDTO> findAllQna() {
        List<QnaBoard> qnaBoards = qnaBoardJpaRepository.findAllQna();

        return qnaBoards.stream()
                .map(QnaBoardResponse.ListDTO::new)
                .toList();
    }

    // 상세 보기
    public QnaBoardResponse.DetailDTO detail(Long qnaBoardId, SessionUser sessionUser) {
        QnaBoard qnaBoard = qnaBoardJpaRepository.findByIdWithDetail(qnaBoardId)
                .orElseThrow(() -> new Exception404("문의 내역을 찾을 수 없습니다"));

        String answer = qnaAnswerJpaRepository.findByQnaBoardId(qnaBoardId)
                .map(QnaAnswer::getContent)
                .orElse("");
        return new QnaBoardResponse.DetailDTO(qnaBoard, answer);
    }

    // 문의 작성
    @Transactional
    public QnaBoardResponse.QnaBoardResponseDTO save(QnaBoardRequest.SaveDTO saveDTO, SessionUser sessionUser){
        User user = userService.getUserId(sessionUser.getId());

        QnaBoard qnaBoard = saveDTO.toEntity(user);
        QnaBoard savedqnaBoard = qnaBoardJpaRepository.save(qnaBoard);
        return new QnaBoardResponse.QnaBoardResponseDTO(savedqnaBoard);

    }

    // 문의 수정
    @Transactional
    public QnaBoardResponse.QnaBoardResponseDTO update(Long qnaBoardId, QnaBoardRequest.UpdateDTO updateDTO,
                                                       SessionUser sessionUser){
        QnaBoard qnaBoard = qnaBoardJpaRepository.findById(qnaBoardId).orElseThrow(() ->
                new Exception404("해당 문의가 존재하지 않습니다"));
        if(!qnaBoard.isOwner(sessionUser.getId())){
            throw new Exception403("본인이 작성한 문의만 수정할 수 있습니다");
        }
        qnaBoard.update(updateDTO);
        return new QnaBoardResponse.QnaBoardResponseDTO(qnaBoard);
    }

    // 문의 삭제
    @Transactional
    public void deleteById(Long qnaBoardId, SessionUser sessionUser) {
        QnaBoard qnaBoard = qnaBoardJpaRepository.findById(qnaBoardId).orElseThrow(() ->
                new Exception404("삭제하려는 문의가 없습니다"));

        if(sessionUser.getRole().equals(Role.USER) && !qnaBoard.isOwner(sessionUser.getId())){
            throw new Exception403("본인이 작성한 문의만 삭제할 수 있습니다");
        }
        qnaBoardJpaRepository.deleteById(qnaBoardId);
    }

}
