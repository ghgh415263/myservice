package com.cloudservice.myservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TokenProviderTest {

    private TokenProvider tokenProvider;

    @BeforeEach
    public void beforeEach() {
        tokenProvider = new TokenProvider();
    }

    @Test
    void 토큰_생성(){
        Token token = tokenProvider.generateToken(111l);
        assertThat(token.getAccessToken()).isNotNull();
    }

    @Test
    void 토큰에서_ID가져옴(){
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTExLCJleHAiOjE3MzAzNzg5MDB9.GWRekI9b1dCKFgdE3J4qN90OvsQBXxGVhCEODIKEIhc";
        Long id = tokenProvider.getMemberId(jwtToken);
        assertThat(id).isEqualTo(111);
    }
}
