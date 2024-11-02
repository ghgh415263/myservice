package com.cloudservice.myservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TokenProviderTest {

    private TokenProvider tokenProvider;

    private String encodedSecretKey = "VG9rZW5Qcm92aWRlclRlc3RUb2tlblByb3ZpZGVyVGVzdFRva2VuUHJvdmlkZXJUZXN0VG9rZW5Qcm92aWRlclRlc3Q=";

    private int accessTokenExpiredTime = 180000;

    @BeforeEach
    public void setUp() {
        tokenProvider = new TokenProvider(new AuthYml(encodedSecretKey, accessTokenExpiredTime));
    }

    @Test
    void 토큰_생성(){
        Token token = tokenProvider.generateToken(111l);
        assertThat(token.getAccessToken()).isNotNull();
    }

    @Test
    void 토큰에서_ID가져옴(){

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .expiration(new Date(now.getTime() + 5000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
                .compact();

        assertThat(tokenProvider.getMemberId(jwtToken)).isEqualTo(12);
    }

    @Test
    void 만료된_토큰(){

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .expiration(new Date(now.getTime()-1000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
                .compact();

        assertThatThrownBy(() -> tokenProvider.getMemberId(jwtToken))
                .isInstanceOf(InValidTokenException.class);
    }

    @Test
    void SecretKey가_다른_토큰(){

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .expiration(new Date(now.getTime()+5000))
                .signWith(Keys.hmacShaKeyFor("이것은 문제가 있는 시크릿키입니다.".getBytes()))
                .compact();

        assertThatThrownBy(() -> tokenProvider.getMemberId(jwtToken))
                .isInstanceOf(InValidTokenException.class);
    }


}
