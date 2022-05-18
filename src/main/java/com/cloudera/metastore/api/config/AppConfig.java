package com.cloudera.metastore.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app")
@Data
public class AppConfig {

    private AppCacheConfig cache = new AppCacheConfig();
    private AppAuthConfig auth = new AppAuthConfig();

    @Data
    public static class AppCacheConfig {
        private long timeout = 300L;
    }

    @Data
    public static class AppAuthConfig {
        private String username = "user";
        private String password = "password";
        private File credentialsFile;
        private File ipWhiteListFile;
    }

}
