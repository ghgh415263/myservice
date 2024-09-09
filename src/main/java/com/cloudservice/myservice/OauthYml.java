package com.cloudservice.myservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("kakao")
@RequiredArgsConstructor
@ToString
public class OauthYml {
    private final String clientSecret;
    private final String grantType;
    private final String clientId;
    private final String redirectUri;
}