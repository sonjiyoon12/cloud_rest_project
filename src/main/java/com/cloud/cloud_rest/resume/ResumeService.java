package com.cloud.cloud_rest.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;

    // 이력서 목록 조회
    public List<ResumeResponse.ListDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("resumeId").descending());
        Page<Resume> resumePage = resumeJpaRepository.findByAllUser(pageable);
        List<ResumeResponse.ListDTO> resumeList = new ArrayList<>();
        for(Resume resume : resumePage.getContent()) {
            ResumeResponse.ListDTO listDTO = new ResumeResponse.ListDTO(resume);
            resumeList.add(listDTO);
        }
        return resumeList;
    }

//    // 이력서 상세보기
//    public ResumeResponse.DetailDTO detail(Long resumeId)






}
