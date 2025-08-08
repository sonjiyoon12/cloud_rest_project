package com.cloud.cloud_rest.faqBoard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FAQBoardService {

    private final FAQBoardJpaRepository faqBoardJpaRepository;

    // 전체 조회
    public List<FAQBoardResponse.ListDTO> findAllFAQ() {
        List<FAQBoard> faqBoards = faqBoardJpaRepository.findAllFQA();

        return faqBoards.stream()
                .map(FAQBoardResponse.ListDTO::new)
                .toList();
    }
}
