package com.cloudservice.myservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class TokenProvider {

    private String secretKey = "tmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmptmp";  //일정 길이 이상으로 해야함

    private int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30;

    public Token generateToken(Long id){
        Date now = new Date();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        String jwtToken = Jwts.builder()
                .claim("id", id)
                .expiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
        return new Token(jwtToken);
    }

}
