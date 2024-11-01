package com.cloudservice.myservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ContextConfiguration(classes = AuthYml.class)
@TestPropertySource(locations = "classpath:application-test.yml", factory = YamlPropertySourceFactory.class)
public class TokenProviderTest {

    private TokenProvider tokenProvider;

    @BeforeEach
    public void beforeEach() {
        tokenProvider = new TokenProvider(new AuthYml("VG9rZW5Qcm92aWRlclRlc3RUb2tlblByb3ZpZGVyVGVzdFRva2VuUHJvdmlkZXJUZXN0VG9rZW5Qcm92aWRlclRlc3Q=", 180000));
    }

    @Test
    void 토큰_생성(){
        Token token = tokenProvider.generateToken(111l);
        assertThat(token.getAccessToken()).isNotNull();
    }

    @Test
    void 토큰에서_ID가져옴(){
        String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MTExLCJleHAiOjE3MzA0Njg1MDJ9.QFxcuj66UE2oaw8ZY3b7e411UO4mVDWNeiFZHYUD23EB3DcLfSv66QQ_5FM-1fnr0aFTBjkx0XfnSfcwCFHmaA";
        assertThat(tokenProvider.getMemberId(jwtToken)).isEqualTo(111);
    }
}
