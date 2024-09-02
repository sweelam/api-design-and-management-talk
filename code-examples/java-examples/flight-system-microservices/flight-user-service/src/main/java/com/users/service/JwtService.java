package com.users.service;

import com.users.exceptions.UserApiException;
import com.users.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final UserRepo userRepo;
    private final String privateKey;

    public JwtService(UserRepo userRepo,
                      @Value("${app.private-key}") final String privateKey) {
        this.userRepo = userRepo;
        this.privateKey = privateKey;
    }

    public String generateJwt(String username, String password) {
        return userRepo.findByUsername(username)
                .map(user -> Jwts.builder()
                        .subject(   user.getUsername())
                        .claim("roles", user.getAuthorities())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                        .signWith(getSignInKey())
                        .compact()
                )
                .orElseThrow(() -> new UserApiException("user not found"));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
        byte[] keyDecodes = Base64.getDecoder().decode(keyBytes);
        return new SecretKeySpec(keyDecodes, "HmacSHA256");
    }
}
