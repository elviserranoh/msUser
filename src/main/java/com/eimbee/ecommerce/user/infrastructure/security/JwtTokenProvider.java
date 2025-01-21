package com.eimbee.ecommerce.user.infrastructure.security;

import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.model.UserRole;
import com.eimbee.ecommerce.user.domain.ports.in.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_TYPE = "JWT";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:864000}")
    private int jwtExpirationToken;

    @Value("${jwt.refresh:600}")
    private int jwtRefreshToken;


    @Override
    public String generateToken(User user) {
        // Configurar las claims del token
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("roles", getAuthorities(user.getRoles()).toString());
        claims.put("tokenType", TOKEN_TYPE);

        return createToken(claims, user, jwtExpirationToken);
    }

    @Override
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("roles", getAuthorities(user.getRoles()).toString());
        claims.put("tokenType", "refresh");
        return createToken(claims, user, jwtRefreshToken);
    }

    @Override
    public UUID extractUserId(String token) {
        Claims claims = getAllClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    @Override
    public String extractUserEmail(String token) {
        Claims claims = getAllClaims(token);
        return claims.get("email", String.class);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    @Override
    public Date getExpirationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    private SecretKey getPublicSigningKey() {
        // Genera una clave segura a partir del secreto
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private String createToken(Map<String, Object> claims, User user, Integer jwtExpiration) {
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtExpiration * 60 * 1000L));
        return Jwts.builder()
                .claims()
                .add(claims)
                .expiration(tokenExpirationDate)
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(String.valueOf(user.getId()))
                .and()
                .signWith(getPublicSigningKey())
                .compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getPublicSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> roles) {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getName()))
                .collect(Collectors.toList());
    }
}
