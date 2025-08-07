package com.cloud.cloud_rest.bulletin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BulletinService {

    private final BulletinRepository bulletinRepository;
    private final UserRepository userRepository;
    private final FileUploadUtil fileUploadUtil;
    private final Base64FileConverterUtil base64FileConverterUtil;
    private final UploadProperties uploadProperties;

    public List<BulletinResponse.DTO> findAll() {
        List<Bulletin> bulletins = bulletinRepository.findAll(Sort.by(Sort.Direction.DESC, "bulletinId"));
        return bulletins.stream().map(BulletinResponse.DTO::new).collect(Collectors.toList());
    }

    public BulletinResponse.DetailDTO findById(Long id) {
        Bulletin bulletin = bulletinRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        return new BulletinResponse.DetailDTO(bulletin);
    }

    @Transactional
    public BulletinResponse.DetailDTO save(BulletinRequest.SaveDTO saveDTO, SessionUser sessionUser) throws IOException {
        String savedImagePath = null;
        String base64Image = saveDTO.getBase64Image();

        if (base64Image != null && !base64Image.isBlank()) {
            MultipartFile convertedFile = base64FileConverterUtil.convert(base64Image);
            // assuming 'bulletin' is the directory for bulletin images
            savedImagePath = fileUploadUtil.uploadProfileImage(convertedFile, "bulletin");
        }

        User user = userRepository.findById(sessionUser.getUserId())
                .orElseThrow(() -> new Exception404("존재하지 않는 사용자 ID 입니다: " + sessionUser.getUserId()));

        Bulletin bulletin = saveDTO.toEntity(user, savedImagePath);
        Bulletin savedBulletin = bulletinRepository.save(bulletin);

        return new BulletinResponse.DetailDTO(savedBulletin);
    }

    @Transactional
    public BulletinResponse.DetailDTO update(Long id, BulletinRequest.UpdateDTO requestDTO, SessionUser sessionUser) {
        Bulletin bulletin = bulletinRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (!bulletin.isOwner(sessionUser.getUserId())) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        bulletin.setTitle(requestDTO.getTitle());
        bulletin.setContent(requestDTO.getContent());
        bulletin.setImagePath(requestDTO.getImagePath());

        return new BulletinResponse.DetailDTO(bulletin);
    }

    @Transactional
    public void delete(Long id, SessionUser sessionUser) {
        Bulletin bulletin = bulletinRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (!bulletin.isOwner(sessionUser.getUserId())) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        bulletinRepository.delete(bulletin);
    }
}
