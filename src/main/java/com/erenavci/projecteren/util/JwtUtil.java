package com.erenavci.projecteren.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("🔐 Token üretildi: " + token);
        return token;
    }

    public String getUsernameFromToken(String token) {
        String username = parseClaims(token).getSubject();
        System.out.println("📛 Token'dan alınan kullanıcı adı: " + username);
        return username;
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            System.out.println("✅ Token geçerli.");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token geçersiz: " + e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
