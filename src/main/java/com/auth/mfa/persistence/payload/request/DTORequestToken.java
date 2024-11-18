package com.auth.mfa.persistence.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter @AllArgsConstructor
public class DTORequestToken {

    private UUID id;
    private final String tokenType = "Bearer ";
    private String accessToken;
    @NotNull
    private UUID refreshToken;
    private List<String> roles;
}