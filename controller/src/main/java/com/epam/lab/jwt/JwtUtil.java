package com.epam.lab.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private int expirationTimeMs;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateSignedToken(final UserDetails userDetailsForGeneration) {
        return createToken(new HashMap<>(), userDetailsForGeneration.getUsername());
    }

    public Boolean validateToken(final String tokenToValidate, final UserDetails userDetailsToCheckWith) {
        return extractUsername(tokenToValidate).equals(userDetailsToCheckWith.getUsername())
                && !isTokenExpired(tokenToValidate);
    }

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(final String tokenToCheck) {
        return extractExpiration(tokenToCheck).before(new Date());
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(final Map<String, Object> claims, final String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


}