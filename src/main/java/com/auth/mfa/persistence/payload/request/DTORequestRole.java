package com.auth.mfa.persistence.payload.request;

import com.auth.mfa.exception.annotation.UniqueNameRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter @UniqueNameRole(label = "name")
public class DTORequestRole {

    private UUID id;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;
//    private Set<Privilege> privileges = new HashSet<>();
}