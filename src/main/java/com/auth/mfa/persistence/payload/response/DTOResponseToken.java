package com.auth.mfa.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter @AllArgsConstructor
public class DTOResponseToken {

    private final String tokenType = "Bearer ";
    private String accessToken;
    private UUID refreshToken;
    private List<String> roles;
}