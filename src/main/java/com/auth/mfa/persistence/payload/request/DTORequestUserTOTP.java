package com.auth.mfa.persistence.payload.request;

import com.auth.mfa.exception.annotation.*;
import lombok.Getter;

import jakarta.validation.constraints.*;

@Getter @UniqueUsername @UniqueEmail
public class DTORequestUserTOTP {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    @NotNull(message = "{not.null}")
    private Integer totpKey;
}