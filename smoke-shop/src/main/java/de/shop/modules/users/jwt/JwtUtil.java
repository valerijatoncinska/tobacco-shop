package de.shop.modules.users.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис для токенов
 */
@Service
public class JwtUtil {

    private final SecretKey accessTokenKey;
    private final SecretKey refreshTokenKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtUtil(
            @Value("${token.access}") String accessTokenKeyStr,
            @Value("${token.refresh}") String refreshTokenKeyStr) {
        this.accessTokenKey = convertToSecretKey(accessTokenKeyStr);
        this.refreshTokenKey = convertToSecretKey(refreshTokenKeyStr);
        this.accessTokenValidity = 1000 * 60 * 60 * 24 * 7; // 15 минут
        this.refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7 дней
    }

    private SecretKey convertToSecretKey(String keyStr) {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(keyStr);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject, getSigningKey(token));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration, getSigningKey(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, SecretKey key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("tokenType", "access");
        return createToken(claims, userDetails.getUsername(), accessTokenKey, accessTokenValidity);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("tokenType", "refresh");
        return createToken(claims, userDetails.getUsername(), refreshTokenKey, refreshTokenValidity);
    }

    private String createToken(Map<String, Object> claims, String subject, SecretKey key, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        SecretKey key = getSigningKey(token);
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private SecretKey getSigningKey(String token) {
        if (isAccessToken(token)) {
            return accessTokenKey;
        } else if (isRefreshToken(token)) {
            return refreshTokenKey;
        } else {
            throw new IllegalArgumentException("Invalid token type");
        }
    }

    private boolean isAccessToken(String token) {
        Claims claims = extractAllClaims(token, accessTokenKey);
        return "access".equals(claims.get("tokenType"));
    }

    private boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token, refreshTokenKey);
        return "refresh".equals(claims.get("tokenType"));
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token, getSigningKey(token));
        return claims.get("roles", List.class);
    }
}
