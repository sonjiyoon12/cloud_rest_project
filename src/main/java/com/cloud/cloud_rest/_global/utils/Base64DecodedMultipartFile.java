package com.cloud.cloud_rest._global.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/*
* getName() / getOriginalFilename() → 파일 이름 반환
getContentType() → 이미지 타입 판별
isEmpty() → 내용 없는지 확인
getSize() / getBytes() → 파일 크기, 내용 가져오기
getInputStream() → 스트림으로 읽기
transferTo(File dest) → 실제 파일로 저장
* */

public class Base64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String fileName;

    public Base64DecodedMultipartFile(byte[] imgContent, String fileName) {
        this.imgContent = imgContent;
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        if (fileName.endsWith(".png")) return "image/png";
        return "image/jpeg";
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        Files.write(dest.toPath(), imgContent);
    }
}
