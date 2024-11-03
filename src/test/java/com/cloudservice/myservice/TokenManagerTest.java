package com.cloudservice.myservice;

import com.cloudservice.myservice.global.auth.AuthYml;
import com.cloudservice.myservice.global.auth.InValidTokenException;
import com.cloudservice.myservice.global.auth.Token;
import com.cloudservice.myservice.global.auth.TokenManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TokenManagerTest {

    private TokenManager tokenManager;

    private String encodedSecretKey = "VG9rZW5Qcm92aWRlclRlc3RUb2tlblByb3ZpZGVyVGVzdFRva2VuUHJvdmlkZXJUZXN0VG9rZW5Qcm92aWRlclRlc3Q=";

    private int accessTokenExpiredTime = 24;

    @BeforeEach
    public void setUp() {
        tokenManager = new TokenManager(new AuthYml(encodedSecretKey, accessTokenExpiredTime));
    }

    @Test
    void 토큰_생성(){
        Token token = tokenManager.generateToken(111l);
        assertThat(token.getAccessToken()).isNotNull();
    }

    @Test
    void 토큰에서_ID가져옴(){

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(accessTokenExpiredTime, ChronoUnit.HOURS);

        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
                .compact();

        assertThat(tokenManager.getMemberId(jwtToken)).isEqualTo(12);
    }

    @Test
    void 만료된_토큰(){

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS).minus(10, ChronoUnit.SECONDS);
        Instant expiration = issuedAt.minus(1, ChronoUnit.SECONDS);

        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey)))
                .compact();

        assertThatThrownBy(() -> tokenManager.getMemberId(jwtToken))
                .isInstanceOf(InValidTokenException.class);
    }

    @Test
    void SecretKey가_다른_토큰(){

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(accessTokenExpiredTime, ChronoUnit.HOURS);

        String jwtToken = Jwts.builder()
                .claim("id", 12)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor("이것은 문제가 있는 시크릿키입니다.".getBytes()))
                .compact();

        assertThatThrownBy(() -> tokenManager.getMemberId(jwtToken))
                .isInstanceOf(InValidTokenException.class);
    }


}
