package com.task.taskproject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @Author : 최대준
 * @ClassName : TokenProvider
 * @Description : JWT 특정 값 활용 객체
 * @Since : 2024. 06. 24.
 */
@PropertySource("classpath:jwt.yml")
@Service
@Getter
public class TokenProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;
    private final String encryptionKey;

    public TokenProvider(
        @Value("${secret-key}") String secretKey,
        @Value("${expiration-hours}") long expirationHours,
        @Value("${issuer}") String issuer,
        @Value("${encryption-key}") String encryptionKey
    ) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
        this.encryptionKey = encryptionKey;
    }

    public String createToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
