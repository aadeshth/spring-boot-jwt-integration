package com.rest.rest_example.utility;

import com.rest.rest_example.exception.InvalidTokenException;
import com.rest.rest_example.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value(value = "${jwt.secret}")
    private String secreteKey;

    @Value(value = "${jwt.expiration}")
    private Long jwtExpiry;

    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiry))
                .signWith(getKey())
                .compact();
    }
    public Key getKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secreteKey));
    }
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT Token has expired.");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid JWT Token.");
        }
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT Token has expired.");
        } catch (JwtException e) {
            throw new InvalidTokenException("Unable to parse JWT token.");
        }
    }
}
