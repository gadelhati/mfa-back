package com.auth.mfa.persistence.payload.request;

import com.auth.mfa.exception.annotation.*;
import lombok.Getter;

import jakarta.validation.constraints.*;
import java.util.UUID;

@Getter @UniqueUsername @UniqueEmail
public class DTORequestUserPassword {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
}