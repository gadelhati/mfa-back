package com.auth.mfa.persistence.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class DTOResponseAuth {

    private String accessToken;
    private String tokenType = "Bearer ";
    private String refreshToken;
    private List<String> roles;
}