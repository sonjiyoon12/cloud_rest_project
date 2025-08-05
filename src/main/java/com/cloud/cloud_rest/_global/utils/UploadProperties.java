package com.cloud.cloud_rest._global.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadProperties {
    private String rootDir;
    private String corpDir;
    private String userDir;
    private String boardDir;
}
