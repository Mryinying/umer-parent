package com.umer.alihealth.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "token"
)
public class TokenProperties {
    private String secretKey;
    private Long tokenExpireSecond;
}
