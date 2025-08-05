package com.cloud.cloud_rest._global.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class Base64FileConverterUtil {
    public static MultipartFile convert(String base64) {
        if (base64 == null || base64.isBlank()) return null;

        // "data:image/png;base64,xxxx..." 형태 처리
        String[] parts = base64.split(",");
        String meta = parts[0]; // data:image/png;base64
        String imageString = parts.length > 1 ? parts[1] : parts[0];

        // 확장자 추출
        String extension = "jpg";
        if (meta.contains("png")) extension = "png";
        else if (meta.contains("jpeg") || meta.contains("jpg")) extension = "jpg";

        byte[] decoded = Base64.getDecoder().decode(imageString);
        return new Base64DecodedMultipartFile(decoded, "image." + extension);

        // JSON에서 들어온 userImageBase64 문자열을 받아서
        //"data:image/png;base64,xxxxxxxx..." 같은 형태를 처리
        //MIME 정보(png, jpeg) 확인 후 파일 확장자 결정
        //Base64 문자열 → byte[] 로 변환
        //MultipartFile을 구현한 Base64DecodedMultipartFile로 감싸 반환
    }
}
