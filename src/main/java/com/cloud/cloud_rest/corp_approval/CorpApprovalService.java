package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.AuthorizationUtil;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corp.CorpService;
import com.cloud.cloud_rest.corp.CorpStatus;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpApprovalService {

    private final CorpApprovalRepository corpApprovalRepository;
    private final CorpRepository corpRepository;
    private final UserService userService;

    @Transactional
    public CorpApprovalResponse.ApprovalDTO userApproval(Long id,SessionUser sessionUser,CorpApprovalRequest.ApprovalDTO corpApprovalRequest){

        AuthorizationUtil.validateAdminAccess(sessionUser); // 관리자인지 체크하기

        Corp corp = corpRepository.findById(id)
                .orElseThrow(() -> new Exception404("기업이 존재하지 않습니다"));

        if(corp.getCorpStatus().equals(CorpStatus.APPROVED)){
            throw new Exception403("이미 승인된 기업입니다");
        }

        if(corp.getCorpStatus().equals(CorpStatus.REJECTED)){
            throw new Exception403("이미 거부가 된 기업입니다");
        }

        User adminUser = userService.getUserId(sessionUser.getId());
        CorpApproval corpApproval = corpApprovalRepository.save(corpApprovalRequest.toEntity(corp,adminUser));
        corp.setCorpStatus(corpApprovalRequest.getCorpStatus()); // 기존 기업에 있던 Status 업데이트
        corpRepository.save(corp);
        return new CorpApprovalResponse.ApprovalDTO(corpApproval);
    }

    // 승인이 거부된 기업에 대해서 로그인하면 Exception 을 던져준다
    public String getLatestRejectionReason(Long corpId) {
        return corpApprovalRepository
                .findTopByCorpIdAndCorpStatusOrderByCreatedAtDesc(corpId, CorpStatus.REJECTED)
                .map(CorpApproval::getText)
                .orElse("거부 사유가 등록되지 않았습니다.");
    }
}
