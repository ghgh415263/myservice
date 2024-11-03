package com.cloudservice.myservice.global.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("jwt")
@ToString
public class AuthYml {
    private final String encodedSecretKey;
    private final int accessTokenExpiredTime;

    public AuthYml(String encodedSecretKey, int accessTokenExpiredTime) {
        this.encodedSecretKey = encodedSecretKey;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }
}