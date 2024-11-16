package com.auth.mfa.persistence.payload.request;

//import com.auth.mfa.exception.annotation.ExceptionRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DTORequestAuth {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String password;
    private String secret;
//    @NotNull @ExceptionRange(value={1,3})
//    private Integer value;
}