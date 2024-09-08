package com.cloudservice.myservice.domain;

import com.cloudservice.myservice.OauthYml;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OauthProvider {

    private final OauthYml oauthYml;

    private final RestClient restClient = RestClient.create();

    public KaKaoAccessTokenRes getAccessToken(String authorizationCode) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "e385bf5e38f12aa2aef69ac404656930");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", authorizationCode);
        params.add("client_secret", oauthYml.getClientSecret());

        return restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(KaKaoAccessTokenRes.class);
    }

    public KakaoMember getMember(String accessToken) {
        return restClient.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer "  + accessToken)
                .retrieve()
                .body(KakaoMember.class);
    }
}
