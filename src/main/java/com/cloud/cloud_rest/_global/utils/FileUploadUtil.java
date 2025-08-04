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

    /**
     * 파일 업로드 후 상대 경로를 반환 (DB 저장용)
     */
    public String uploadProfileImage(MultipartFile multipartFile, String subDirName) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        // ./uploads/corp-images/
        String fullUploadPath = Paths.get(uploadPath.getRootDir(), subDirName).toString();
        createUploadDirectory(fullUploadPath);

        String originFilename = multipartFile.getOriginalFilename();
        String extension = getFileExtension(originFilename);
        String uniqueFileName = generateUniqueFileName(extension);

        Path filePath = Paths.get(fullUploadPath, uniqueFileName);
        multipartFile.transferTo(filePath);
        return Paths.get(subDirName, uniqueFileName).toString().replace("\\", "/");
    }

    /**
     * 기존 파일 삭제
     */
    public void deleteProfileImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // ./uploads/corp-images/abc.jpg
                Path filePath = Paths.get(uploadPath.getRootDir(), imagePath);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new Exception400("프로필 이미지를 삭제하지 못했습니다");
            }
        }
    }

    /**
     * 업로드 경로 생성
     */
    private void createUploadDirectory(String fullUploadPath) throws IOException {
        Path uploadDir = Paths.get(fullUploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String originFilename) {
        if (originFilename == null || originFilename.lastIndexOf(".") == -1) {
            return "";
        }
        return originFilename.substring(originFilename.lastIndexOf("."));
    }

    /**
     * 파일명 생성: YYYYMMDD_HHmmss_UUID.ext
     */
    private String generateUniqueFileName(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + extension;
    }
}
