package com.cloudservice.myservice.global.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final AuthYml authYml;

    public Token generateToken(Long id){

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(authYml.getAccessTokenExpiredTime(), ChronoUnit.HOURS);

        String jwtToken = Jwts.builder()
                .claim("id", id)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
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
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            throw new InValidTokenException(e);
        }
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(authYml.getEncodedSecretKey()));
    }

}
