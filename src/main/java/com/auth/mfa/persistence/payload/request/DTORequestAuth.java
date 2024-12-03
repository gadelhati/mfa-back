package com.auth.mfa.persistence.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DTORequestAuth {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String password;
    @NotNull(message = "{not.null}")
    private Integer totpKey;
}