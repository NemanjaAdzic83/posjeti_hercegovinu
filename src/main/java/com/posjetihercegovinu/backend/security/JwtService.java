package com.posjetihercegovinu.backend.security;


import com.posjetihercegovinu.backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


// Pravi i cita tokene
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    // Konstruktor- cita kljuc i trajanje iz properties fajla i pravi kriptografski kljuc
    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    // Pravi potpisan token za korisnika. U njega upisuje email(subject), ulogu i rok trajanja
    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role",user.getRole().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    // Provjerav potpis i rok i vraca email iz tokena. Ako token nje valjan, vraca null
    public String extractEmail(String token){
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return claims.getSubject();
        }catch (JwtException | IllegalArgumentException e){

            return null;
        }
    }


}
