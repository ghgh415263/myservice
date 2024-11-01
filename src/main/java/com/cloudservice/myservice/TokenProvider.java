package com.cloudservice.myservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final AuthYml authYml;

    public Token generateToken(Long id){
        Date now = new Date();
        String jwtToken = Jwts.builder()
                .claim("id", id)
                .expiration(new Date(now.getTime() + authYml.getAccessTokenExpiredTime()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(authYml.getEncodedSecretKey())))
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
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(authYml.getEncodedSecretKey()));
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims;
    }

}
