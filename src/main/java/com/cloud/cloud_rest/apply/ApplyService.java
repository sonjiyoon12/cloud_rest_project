package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.resume.ResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final RecruitRepository recruitJpaRepository;

    // 공고 지원
    @Transactional
    public ApplyResponse.SaveDTO save(Long resumeId, Long recruitId) {
        Resume resume = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));

        Recruit recruit = recruitJpaRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404("공고글을 찾을 수 없습니다."));

        ApplyRequest.SaveDTO saveDTO = new ApplyRequest.SaveDTO();
        Apply apply = saveDTO.toEntity(resume, recruit);
        applyJpaRepository.save(apply);

        return new ApplyResponse.SaveDTO(apply);
    }

    // 전체 공고 지원 내역 조회
    public List<ApplyResponse.DetailDTO> findAll() {
        List<Apply> applies = applyJpaRepository.findAll();
        List<ApplyResponse.DetailDTO> detailDTOS = new ArrayList<>();

        return applies.stream()
                .map(ApplyResponse.DetailDTO::new)
                .toList();
    }

    // 특정 공고 지원 내역 조회
    public ApplyResponse.DetailDTO findById(Long applyId) {
        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new Exception404("공고 지원 내역을 찾을 수 없습니다."));

        return new ApplyResponse.DetailDTO(apply);
    }

    // 특정 공고 지원 내역 삭제
    @Transactional
    public void deleteById(Long id) {
        Apply apply = applyJpaRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        applyJpaRepository.delete(apply);
    }
}
