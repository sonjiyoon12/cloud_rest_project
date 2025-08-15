package com.cloud.cloud_rest.loginhistory;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginHistoryService {
    private final LoginHistoryRepository historyRepository;


    @Transactional
    public void saveUser(User user, LoginHistoryRequest.SaveUserDTO saveUserDTO, String ipAddress, String userAgent){
        historyRepository.save(saveUserDTO.toEntity(user,ipAddress,userAgent));
    }
    @Transactional
    public void saveCorp(Corp corp, LoginHistoryRequest.SaveCorpDTO saveCorpDTO, String ipAddress, String userAgent){
        historyRepository.save(saveCorpDTO.toEntity(corp,ipAddress,userAgent));
    }

    @Transactional
    public void deactivateLatest(Long id, Role role) {
        Optional<LoginHistory> latest;

        if (role == Role.USER) {
            latest = historyRepository.findTopByUserId(id);
        } else if (role == Role.CORP) {
            latest = historyRepository.findTopByCorpId(id);
        } else if (role == Role.ADMIN) {
            latest = historyRepository.findTopByAdminId(id);
        } else {
            throw new IllegalArgumentException("알 수 없는 Role: " + role);
        }

        latest.ifPresent(LoginHistory::deactivate);
    }

    @Transactional
    public void deactivateAllActiveCorpLogins(Long corpId) {
        historyRepository.deactivateAllActiveByCorpId(corpId);
    }

    @Transactional
    public void deactivateAllActiveUserLogins(Long corpId) {
        historyRepository.deactivateAllActiveByUserId(corpId);
    }


}
