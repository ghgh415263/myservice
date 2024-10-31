package com.cloudservice.myservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    private String secretKey = "tmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmp";  //일정 길이 이상으로 해야함

    private int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(Long id){
        Date now = new Date();
        String jwtToken = Jwts.builder()
                .claim("id", id)
                .expiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .compact();
        return new Token(jwtToken);
    }

    public Long getMemberId(String jwtToken){
        Claims claims = getClaims(jwtToken);
        return claims.get("id", Long.class);
    }

    public boolean isValidToken(String jwtToken) {
        Claims claims = getClaims(jwtToken);
        return claims.getExpiration().before(new Date());
    }

    private Claims getClaims(String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims;
    }

}
