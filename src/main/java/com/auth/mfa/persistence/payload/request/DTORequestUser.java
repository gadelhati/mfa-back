package com.auth.mfa.persistence.payload.request;

import com.auth.mfa.exception.annotation.*;
import com.auth.mfa.persistence.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter @UniqueUsername
@UniqueEmail
public class DTORequestUser {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @HasLength
    private String username;
    @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    private String email;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    @HasDigit
    @HasLetter
    @HasUpperCase
    @HasLowerCase
    @HasLength
    private String password;
    @NotNull(message = "{user.active.not.null}")
    private boolean active;
    private String secret;

    private Collection<Role> role;
}