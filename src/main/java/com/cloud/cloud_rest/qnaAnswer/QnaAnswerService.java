package com.cloud.cloud_rest.qnaAnswer;

import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.qnaBoard.QnaBoard;
import com.cloud.cloud_rest.qnaBoard.QnaBoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QnaAnswerService {

    private final QnaAnswerJpaRepository qnaAnswerJpaRepository;
    private final QnaBoardJpaRepository qnaBoardJpaRepository;

    // 답변 작성
    @Transactional
    public QnaAnswerResponse.QnaAnswerResponseDTO save(Long qnaBoardId ,QnaAnswerRequest.SaveDTO saveDTO) {
        QnaBoard qnaBoard = qnaBoardJpaRepository.findById(qnaBoardId)
                .orElseThrow(() -> new Exception404("해당 문의가 존재하지 않습니다"));

        QnaAnswer qnaAnswer = QnaAnswer.builder()
                .content(saveDTO.getContent())
                .qnaBoard(qnaBoard)
                .build();

        qnaAnswerJpaRepository.save(qnaAnswer);
        return new QnaAnswerResponse.QnaAnswerResponseDTO(qnaAnswer);
    }

    // 답변 수정
    @Transactional
    public QnaAnswerResponse.QnaAnswerResponseDTO update(Long qnaAnswerId, QnaAnswerRequest.UpdateDTO updateDTO) {
        QnaAnswer qnaAnswer = qnaAnswerJpaRepository.findById(qnaAnswerId)
                .orElseThrow(() -> new Exception404("해당 답변이 존재하지 않습니다"));

        qnaAnswer.update(updateDTO);
        return new QnaAnswerResponse.QnaAnswerResponseDTO(qnaAnswer);
    }

    // 답변 삭제
    @Transactional
    public void deleteById(Long qnaAnswerId) {
        QnaAnswer qnaAnswer = qnaAnswerJpaRepository.findById(qnaAnswerId)
                .orElseThrow(() -> new Exception404("해당 답변이 존재하지 않습니다"));

        qnaAnswerJpaRepository.deleteById(qnaAnswerId);

    }

}
