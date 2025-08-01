package com.cloud.cloud_rest.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;

    // 공고 지원
    public ApplyResponse.SaveDTO save(ApplyRequest.SaveDTO saveDTO) {
        return null;
    }

    // 전체 공고 지원 내역 조회
    public List<ApplyResponse.DetailDTO> findAll() {
        return null;
    }

    // 특정 공고 지원 내역 조회
    public ApplyResponse.SaveDTO findById(Long id) {
        return null;
    }

    // 특정 공고 지원 내역 삭제
    public void deleteById(Long id) {
    }
}
