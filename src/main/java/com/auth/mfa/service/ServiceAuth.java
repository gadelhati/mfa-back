package com.auth.mfa.service;

import com.auth.mfa.persistence.MapperInterface;
import com.auth.mfa.persistence.model.Token;
import com.auth.mfa.persistence.payload.request.DTORequestAuth;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import com.auth.mfa.persistence.repository.RepositoryToken;
import com.auth.mfa.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ServiceAuth {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final RepositoryToken repositoryToken;
    private final ServiceToken serviceToken;
    private final MapperInterface<Token, DTORequestToken, DTOResponseToken> mapperInterface;
    private final ServiceCustomUserDetails serviceCustomUserDetails;
    private final ServiceTOTP serviceTOTP;

    public DTOResponseToken login(DTORequestAuth dtoRequestAuth) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoRequestAuth.getUsername(), dtoRequestAuth.getPassword()));
        serviceTOTP.validateTOTP(authentication.getName(), dtoRequestAuth.getTotpKey());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(dtoRequestAuth.getUsername());
        String token = jwtGenerator.generateToken(authentication.getName());
        UUID refreshToken = UUID.randomUUID();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        serviceToken.create(new DTORequestToken(UUID.randomUUID(), "token", refreshToken, roles));
        return new DTOResponseToken(token, refreshToken, roles);
    }

    public DTOResponseToken refresh(DTORequestToken dtoRequestToken) {
        if (repositoryToken.existsByRefreshToken(dtoRequestToken.getRefreshToken()) &&
            jwtGenerator.validateJWT(dtoRequestToken.getAccessToken())) {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(
                    jwtGenerator.getUsernameFromJWT(dtoRequestToken.getAccessToken())
            );
            String tokenResponse = jwtGenerator.generateToken(jwtGenerator.getUsernameFromJWT(dtoRequestToken.getAccessToken()));
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return new DTOResponseToken(tokenResponse, dtoRequestToken.getRefreshToken(), roles);
        } else {
            logout(dtoRequestToken.getRefreshToken());
            return null;
        }
    }

    public DTOResponseToken logout(UUID refreshToken) {
        return repositoryToken.findByRefreshToken(refreshToken)
                .map(token -> {
                    repositoryToken.deleteById(token.getId());
                    return mapperInterface.toDTO(token);
                })
                .orElseThrow(() ->
                    new IllegalArgumentException("Token not found.")
                );
    }
}