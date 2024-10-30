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
        Token token = tokenProvider.generateToken(1l);
        assertThat(token.getAccessToken()).isNotNull();
    }
}
