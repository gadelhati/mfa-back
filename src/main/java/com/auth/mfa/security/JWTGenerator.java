package com.auth.mfa.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JWTGenerator {
    private static final Logger logger = Logger.getLogger(JWTGenerator.class.getName());

    @Value("${app.jwtIssuer}")
    private String issuer;
    @Value("${app.jwtAudience}")
    private String audience;
    private final SecretKey secretKey = new SecretKeySpec(new byte[64], "HmacSHA512");

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .audience().add(audience).and()
                .header().add("typ", "JWT").and()
                .issuer(issuer)
                .subject(authentication.getName())
                .notBefore(new Date())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + SecurityConstants.JWT_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }
    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            logger.warning("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warning("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.warning("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warning("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warning("JWT claims string is empty: " + e.getMessage());
        } catch (Exception ex) {
            logger.warning("validateToken, exception: " + ex);
        }
        return false;
    }
}