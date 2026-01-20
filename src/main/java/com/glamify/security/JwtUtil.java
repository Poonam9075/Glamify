package com.glamify.security;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // SECRET KEY (must be same for generate + validate)
    private static final Key SECRET_KEY =
            Keys.hmacShaKeyFor("glamify-secret-key-glamify-secret-key".getBytes());

    // Token validity (24 hours)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    // ================== GENERATE TOKEN ==================
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)          // CUSTOMER / ADMIN / PROFESSIONAL
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // ================== VALIDATE TOKEN ==================
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================== EXTRACT USERNAME ==================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ================== EXTRACT ROLE ==================
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ================== PARSE CLAIMS ==================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
