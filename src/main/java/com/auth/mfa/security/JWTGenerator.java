package com.auth.mfa.security;

import com.auth.mfa.MfaApplication;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JWTGenerator {
    private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);
    @Value("${application.jwtIssuer}")
    private String issuer;
    @Value("${application.jwtAudience}")
    private String audience;
    @Value("${application.jwtExpiration}")
    private Integer expiration;
    private final SecretKey secretKey = new SecretKeySpec(new byte[64], "HmacSHA512");

    public String generateToken(String authentication) {
        return Jwts.builder()
                .audience().add(audience).and()
                .header().add("typ", "JWT").and()
                .issuer(issuer)
                .subject(authentication)
                .notBefore(new Date())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
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
    public boolean validateJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(token).getPayload();
            return true;
        } catch (SecurityException e) {
            LOGGER.error("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: " + e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("validateToken, exception: " + ex);
        }
        return false;
    }
}