package com.auth.mfa.controller;

import com.auth.mfa.persistence.payload.request.DTORequestAuth;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import com.auth.mfa.service.ServiceAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @DeleteMapping("/logout/{refreshToken}")
    public ResponseEntity<DTOResponseToken> logout(@PathVariable("refreshToken") UUID refreshToken) {
        return ResponseEntity.accepted().body(serviceAuth.logout(refreshToken));
    }
}