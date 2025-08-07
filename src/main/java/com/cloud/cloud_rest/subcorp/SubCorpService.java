package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.Define;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SubCorpResponse.SaveDTO saveSubCorp(SubCorpRequest.SaveDTO saveDTO, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> {
            throw new Exception400(Define.SaveDTO.ERROR_400);
        });

        Corp corp = corpRepository.findById(saveDTO.getCorpId())
                .orElseThrow(() -> new Exception404("존재하지 않는 기업입니다."));

        SubCorp subCorp = subCorpJpaRepository.findByUserUserIdAndCorpCorpId(user.getUserId(), corp.getCorpId());

        if (subCorp == null) {
            subCorp = saveDTO.toEntity(user, corp);
            subCorpJpaRepository.save(subCorp);
            return new SubCorpResponse.SaveDTO(subCorp);
        } else {
            throw new Exception400("이미 구독되어 있는 기업입니다.");
        }
    }

    // 구독한 기업 리스트 목록
    public List<SubCorpResponse.DetailDTO> findAll(Long userId, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다."));

        if (!userId.equals(user.getUserId())) {
            throw new Exception403("볼 수 있는 권한이 없습니다.");
        }

        List<SubCorp> subCorps = subCorpJpaRepository.findByUser(user.getUserId());
        return subCorps.stream()
                .map(SubCorpResponse.DetailDTO::new)
                .toList();
    }

    // 구독 기업 삭제
    public void delete(Long id, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("유저를 찾을 수 없습니다."));

        SubCorp subCorp = subCorpJpaRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 구독 내역을 찾을 수 없습니다"));

        if (!user.getUserId().equals(sessionUser.getId())) {
            throw new Exception403("삭제할 수 있는 권한이 없습니다.");
        }

        subCorpJpaRepository.delete(subCorp);
    }
}
