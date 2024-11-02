package com.cloudservice.myservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final AuthYml authYml;

    public Token generateToken(Long id){
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = Jwts.builder()
                .claim("id", id)
                .expiration(new Date(now.getTime() + authYml.getAccessTokenExpiredTime()))
                .signWith(getSecretKey())
                .compact();
        return new Token(jwtToken);
    }

    public Long getMemberId(String jwtToken){
        Claims claims = getClaims(jwtToken);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getPayload();
            return claims;
        } catch (SignatureException | ExpiredJwtException e) {
            throw new InValidTokenException(e);
        }
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(authYml.getEncodedSecretKey()));
    }

}
