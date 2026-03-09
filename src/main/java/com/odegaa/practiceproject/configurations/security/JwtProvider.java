package com.odegaa.practiceproject.configurations.security;

import com.odegaa.practiceproject.entities.templates.Roles;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String key;

    @Value("${jwt.expire.time}")
    private long expireTime;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(bytes);
        if (key.length() < 32) {
            log.error("Jwt Secret Key length less than 32");
        } else {
            log.info("Jwt Secret Key initialized successfully!");
        }
    }

    public String generateToken(String username, Set<Roles> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Malformed Jwt Token: {}" + e.getMessage() + " | " + token);
        } catch (SignatureException e) {
            log.error("Invalid JWT token: {}" + e.getMessage());
        } catch (JwtException e) {
            log.error("Token Validation Failed: {}" + e.getMessage());
        }
        return false;
    }

}
