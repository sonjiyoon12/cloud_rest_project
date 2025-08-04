package com.cloud.cloud_rest._global.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class Base64FileConverterUtil {
    public static MultipartFile convert(String base64) throws IOException {
        String[] parts = base64.split(",");
        String imageString = parts.length > 1 ? parts[1] : parts[0];
        byte[] decoded = Base64.getDecoder().decode(imageString);
        return new Base64DecodedMultipartFile(decoded, "image.jpg");
    }
}
