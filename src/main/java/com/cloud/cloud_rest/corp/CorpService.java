package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpService {
    private final CorpRepository corpRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정
    private final Base64FileConverterUtil base64FileConverterUtil;

    @Transactional
    public CorpResponse.CorpDTO save(CorpRequest.SaveDTO saveDTO){
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());

        if(!corpRepository.existsLoginId(saveDTO.getLoginId())){
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        Corp corp = saveDTO.toEntity(bcyPassword);
        corpRepository.save(corp);
        return new CorpResponse.CorpDTO(corp);
    }

    // 로그인 API 
    public String login(CorpRequest.LoginDTO loginDTO){

        Corp corp = getLoginId(loginDTO.getLoginId());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(),corp.getPassword())){
            throw new Exception400("비밀번호가 일치 하지 않습니다");
        }
        return JwtUtil.createForCorp(corp);
    }

    // 수정 API
    @Transactional
    public CorpResponse.UpdateDTO updateDTO(Long id, CorpRequest.UpdateDTO dto) {
        Corp corp = getCorpId(id);

        String oldImagePath = corp.getCorpImage();
        String savedFileName = null;

        try {
            if (dto.getCorpImage() != null && !dto.getCorpImage().isEmpty()) {
                // Multipart 파일 처리
                savedFileName = fileUploadUtil.uploadProfileImage(dto.getCorpImage(), uploadPath.getCorpDir());
            } else if (dto.getCorpImageBase64() != null && !dto.getCorpImageBase64().isEmpty()) {
                // base64 처리: base64 -> MultipartFile 변환
                String base64Image = dto.getCorpImageBase64();
                if (base64Image != null && !base64Image.isEmpty()) {
                    MultipartFile multipartFile = Base64FileConverterUtil.convert(base64Image);
                    savedFileName = fileUploadUtil.uploadProfileImage(multipartFile, uploadPath.getCorpDir());
                }
            }

            if (savedFileName != null && oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }

            corp.update(dto, savedFileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CorpResponse.UpdateDTO(corp);
    }



    public CorpResponse.CorpDTO getCorpInfo(Long id, Long sessionUserId){
        if(!id.equals(sessionUserId)){
            throw new Exception403("자신의 기업 정보만 확인 가능합니다");
        }
        Corp corp = getCorpId(id);
        return new CorpResponse.CorpDTO(corp);
    }

    // Corp Login ID로 해당 유저 찾기
    public Corp getLoginId(String loginId){
        return corpRepository.findByLoginId(loginId)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다"));
    }
    // Corp Id로 해당 유저 찾기
    public Corp getCorpId(Long id){
        return corpRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다"));
    }

}
