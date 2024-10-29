package com.auth.mfa.controller;

import com.auth.mfa.persistence.payload.request.DTORequestAuth;
import com.auth.mfa.persistence.payload.response.DTOResponseAuth;
import com.auth.mfa.service.ServiceAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class ControllerAuth {

    private final ServiceAuth serviceAuth;

    @PostMapping("/login")
    public @ResponseBody DTOResponseAuth login(@RequestBody @Valid DTORequestAuth value){
        return serviceAuth.login(value);
    }
    @PostMapping("/logout")
    public @ResponseBody HttpStatus logout(@Valid @RequestBody DTORequestAuth dtoRequestAuth) {
        try {
            serviceAuth.logout(dtoRequestAuth);
            return HttpStatus.ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}