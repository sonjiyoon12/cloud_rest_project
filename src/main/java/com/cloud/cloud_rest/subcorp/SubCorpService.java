package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.utils.Define;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SubCorpService {

    private final SubCorpJpaRepository subCorpJpaRepository;
    private final UserRepository userRepository;
    private final CorpRepository corpRepository;

    // 구독 저장 기능

    @Transactional
    public void saveSubCorp(Long userId, Long corpId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.SaveDTO.ERROR_400);
        });

//
//         if (subCorpJpaRepository.existsByUserAndCompany(user, orp).isPresent()) {
//            throw new Exception400(Define.SaveDTO.ERROR_400);
//        }

    }




    // 구독한 기업 리스트 목록

    public List<SubCorpResponse.SubCorpResponseDTO> getSubscribedCorps(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400(Define.SaveDTO.ERROR_400);
        });

        List<Corp> corpList = subCorpJpaRepository.findByUser(userId)
                .stream()
                .map(SubCorp::getCorp)
                .toList();

        List<SubCorpResponse.SubCorpResponseDTO> corpInfoList = new ArrayList<>();

        for (Corp corp : corpList) {
            SubCorpResponse.SubCorpResponseDTO corpLists = new SubCorpResponse.SubCorpResponseDTO(corp);
            corpInfoList.add(corpLists);
        }

        return corpInfoList;
    }



}
