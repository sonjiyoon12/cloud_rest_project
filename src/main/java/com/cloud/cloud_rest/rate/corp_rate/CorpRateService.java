package com.cloud.cloud_rest.rate.corp_rate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorpRateService {

    private final CorpRateJpaRepository corpRateJpaRepository;
    private final CorpRepository corpRepository;
    private final UserRepository userRepository;

    @Transactional
    public CorpRateResponse.SaveDTO save(CorpRateRequest.SaveDTO saveDTO, SessionUser sessionUser, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception403("존재하지 않는 유저입니다."));

        Corp corp = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재하지 않는 회사입니다."));

        CorpRate corpRate = corpRateJpaRepository.findByUserUserIdAndCorpCorpId(user.getUserId(), corp.getCorpId());

        if (corpRate != null) {
            throw new Exception400("이미 평점을 남긴 유저입니다.");
        }

        corpRate = saveDTO.toEntity(corp, user);
        corpRateJpaRepository.save(corpRate);
        return new CorpRateResponse.SaveDTO(corpRate);
    }

    public List<CorpRateResponse.DetailDTO> findAll() {
        List<CorpRate> corpRates = corpRateJpaRepository.findAll();
        return corpRates.stream()
                .map(CorpRateResponse.DetailDTO::new)
                .toList();
    }

    public List<CorpRateResponse.DetailDTO> findByUserId(Long userId) {
        List<CorpRate> corpRates = corpRateJpaRepository.findByUserUserId(userId)
                .orElseThrow(() -> new Exception404("해당 유저에 평점이 없습니다."));
        return corpRates.stream()
                .map(CorpRateResponse.DetailDTO::new)
                .toList();
    }

    @Transactional
    public void deleteById(Long corpRateId, SessionUser sessionUser) {
        Corp corp = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception403("평점을 남길 수 있는 권한이 없습니다."));

        CorpRate corpRate = corpRateJpaRepository.findById(corpRateId)
                .orElseThrow(() -> new Exception404("존재하지 않는 평점입니다."));

        if (!corp.getCorpId().equals(corpRate.getCorp().getCorpId())) {
            throw new Exception403("본인만 삭제 가능합니다.");
        }

        corpRateJpaRepository.deleteById(corpRateId);
    }
}
