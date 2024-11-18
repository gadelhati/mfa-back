package com.auth.mfa.controller;

import com.auth.mfa.persistence.payload.request.DTORequestAuth;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import com.auth.mfa.service.ServiceAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class ControllerAuth {

    private final ServiceAuth serviceAuth;

    @PostMapping("/login")
    public @ResponseBody DTOResponseToken login(@RequestBody @Valid DTORequestAuth value){
        return serviceAuth.login(value);
    }
    @PostMapping("/refresh")
    public @ResponseBody DTOResponseToken refresh(@RequestBody @Valid DTORequestToken value){
        return serviceAuth.refresh(value);
    }
    @PostMapping("/logout")
    public @ResponseBody HttpStatus logout(@Valid @RequestBody DTORequestToken dtoRequestToken) {
        try {
            serviceAuth.logout(dtoRequestToken);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}