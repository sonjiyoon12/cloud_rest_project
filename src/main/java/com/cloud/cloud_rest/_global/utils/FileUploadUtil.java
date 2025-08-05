package com.cloud.cloud_rest._global.utils;

import com.cloud.cloud_rest._global.exception.Exception400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadUtil {

    private final UploadProperties uploadPath;

    public String uploadProfileImage(MultipartFile multipartFile, String subDirName) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) return null;

        // ./uploads/corp-images/
        String fullUploadPath = Paths.get(uploadPath.getRootDir(), subDirName).toString();
        createUploadDirectory(fullUploadPath);

        String originFilename = multipartFile.getOriginalFilename(); // 실제 이미지 이름을 담음
        String extension = getFileExtension(originFilename);
        String uniqueFileName = generateUniqueFileName(extension); // 변환 된 이미지 이름을 담음

        Path filePath = Paths.get(fullUploadPath, uniqueFileName);
        multipartFile.transferTo(filePath);

        // DB에는 상대경로 저장
        return Paths.get(subDirName, uniqueFileName).toString().replace("\\", "/");
    }

    // 기존 이미지가 있으면 삭제(해당 경로)
    public void deleteProfileImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) return;
        try {
            Path filePath = Paths.get(uploadPath.getRootDir(), imagePath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지를 삭제하지 못했습니다", e);
        }
    }

    // 업로드 파일이 없으면 생성(저장경로)
    private void createUploadDirectory(String fullUploadPath) throws IOException {
        Path uploadDir = Paths.get(fullUploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }
    // 1. 파일 이름 자체가 null 또는 .이 없는경우 빈 문자열("")을 반환.
    // 2. 마지막 . 부터 확장 까지 짜르기 photo.png -> .png
    private String getFileExtension(String originFilename) {
        if (originFilename == null || originFilename.lastIndexOf(".") == -1) return "";
        return originFilename.substring(originFilename.lastIndexOf("."));
    }

    // 생성된 이미지 파일에 이름 : 현재 날짜및 시간 + 랜덤 8글자 추가
    private String generateUniqueFileName(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + extension;
    }
}
