package com.cloud.cloud_rest.recruit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CorpRepository corpRepository;

    //공고 저장
    @Transactional
    public Recruit save(RecruitRequest.SaveDTO dto) {
        log.info("공고 등록 요청 - corpId: {}, title: {}", dto.getCorpId(), dto.getTitle());

        Corp corp = corpRepository.findById(dto.getCorpId())
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.CORP_NOT_FOUND + dto.getCorpId()));

        Recruit recruit = dto.toEntity(corp);

        return recruitRepository.save(recruit);
    }

    //공고 수정
    @Transactional
    public Recruit update(Long id, RecruitRequest.UpdateDTO dto) {
        log.info("공고 수정 요청 - recruitId: {}, title: {}", id, dto.getTitle());

        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.RECRUIT_NOT_FOUND + id));

        recruit.update(dto.getTitle(), dto.getContent(), dto.getDeadline());

        return recruit;
    }

    // 공고 삭제
    @Transactional
    public void recruitDelete(Long id, Long corpId) throws AccessDeniedException {

        log.info("공고 삭제 요청 - recruitId: {}, corpId: {}", id, corpId);

        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.RECRUIT_NOT_FOUND + id));

        Corp corp = corpRepository.findById(corpId)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.CORP_NOT_FOUND + corpId));

        if(!recruit.isOwner(corp)){
            throw new AccessDeniedException(RecruitErr.ACCESS_DENIED);
        }

        recruitRepository.delete(recruit);
    }

    // 기업별 전체 공고조회
    public List<Recruit> findByCorpId(Long corpId) {
        log.info("기업별 공고 조회 요청 - corpId: {}", corpId);
        return recruitRepository.findByCorpId(corpId);
    }

    // 공고갯수 가져오기
    public Long countByCorpId(Long corpId){
        log.info("기업별 공고 갯수 조회 요청 - corpId: {}", corpId);
        return recruitRepository.countByCorpId(corpId);
    }

}
