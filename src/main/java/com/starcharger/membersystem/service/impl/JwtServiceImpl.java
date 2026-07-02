package com.starcharger.membersystem.service.impl;

import com.starcharger.membersystem.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(Long accountId, String email) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(accountId))
                .claim("email", email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpiration))
                .signWith(getKey())
                .compact();
    }

    @Override
    public Long getAccountId(String jwtToken) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}