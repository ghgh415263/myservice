package com.cloudservice.myservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao")
@PropertySource(value = "classpath:oauth.yml", factory = YamlPropertySourceFactory.class)
public class OauthYml {

    private String clientSecret;
}