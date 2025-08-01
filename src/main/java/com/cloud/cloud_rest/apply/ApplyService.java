package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest.errors.exception.Exception404;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.resume.Resume;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeService resumeService;
    private final RecruitService recruitService;

    // 공고 지원
    public ApplyResponse.SaveDTO save(Long resumeId, Long recruitId) {
        Resume resume = resumeService.findById(resumeId);
        Recruit recruit = recruitService.findById(recruitId);

        if (resume == null || recruit == null) {
            throw new Exception404("이력서 또는 공고글을 찾을 수 없습니다.");
        }

        ApplyRequest.SaveDTO saveDTO = new ApplyRequest.SaveDTO();
        Apply apply = saveDTO.toEntity(resume, recruit);
        applyJpaRepository.save(apply);

        return new ApplyResponse.SaveDTO(apply);
    }

    // 전체 공고 지원 내역 조회
    public List<ApplyResponse.DetailDTO> findAll() {
        return null;
    }

    // 특정 공고 지원 내역 조회
    public ApplyResponse.DetailDTO findById(Long applyId) {
        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new Exception404("공고 지원 내역을 찾을 수 없습니다."));

        return new ApplyResponse.DetailDTO(apply);
    }

    // 특정 공고 지원 내역 삭제
    public void deleteById(Long id) {
    }
}
