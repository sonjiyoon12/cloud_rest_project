package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpService {
    private final CorpRepository corpRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public CorpResponse.CorpDTO save(CorpRequest.SaveDTO saveDTO){
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());
        Corp corp = saveDTO.toEntity(bcyPassword);
        corpRepository.save(corp);
        return new CorpResponse.CorpDTO(corp);
    }

    // 로그인 API 
    public String login(CorpRequest.LoginDTO loginDTO){

        Corp corp = getLoginId(loginDTO.getLoginId());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(),corp.getPassword())){
            throw new Exception403("비밀번호가 일치 하지 않습니다");
        }
        return JwtUtil.createForCorp(corp);
    }

    // 수정 API
    @Transactional
    public CorpResponse.UpdateDTO updateDTO(Long Id,CorpRequest.UpdateDTO updateDTO){
        Corp corp = getCorpId(Id);
        String uploadImage = corp.getCorpImage();

        MultipartFile imageFile = updateDTO.getCorpImage();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                uploadImage = "uploads/" + UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Files.copy(imageFile.getInputStream(), Paths.get(uploadImage));
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }

        corp.update(updateDTO,uploadImage);

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
