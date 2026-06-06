package com.arka;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt-provider.secret}")
    private String secret;

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).toInstant().isBefore(Instant.now());

    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {

        Map<?, ?> roleMap =
                extractClaims(token, claims -> claims.get("role", Map.class));

        if (roleMap == null || roleMap.get("name") == null) {
            return List.of();
        }

        String roleName = roleMap.get("name").toString();

        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    public String extractUserId(String token){
        return extractClaims(token, claims -> claims.get("userId").toString());
    }


    /**
     * Metodos para resolver o extraer cualqueir claim del token validan la firma.
     * se retonara un objeto del tipo de claim solicictado
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSiningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Metodo para extraer la forma del token y que servira como llave para extraer informacion de token.
     * @return
     */

    private SecretKey getSiningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
