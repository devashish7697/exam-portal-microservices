package com.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration){
        if (secret == null && secret.length() < 32){
            throw new IllegalArgumentException("JWT secret must be at least 32 characters");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(Long id, String username, String email, String name, Set<String> roles){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        Map<String,Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("email",email);
        claims.put("roles",roles);
        claims.put("name",name);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Set<String> extractRoles(Claims claims) {
        Object rolesObj = claims.get("roles");
        if (rolesObj == null) return Set.of();
        // JSON array -> List when parsed; convert to Set
        List<String> list = (List<String>) rolesObj;
        return new HashSet<>(list);
    }

    public long getExpirationMs() {
        return expiration;
    }

}
