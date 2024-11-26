package com.auth.mfa.controller;

import com.auth.mfa.persistence.model.User;
import com.auth.mfa.service.ServiceTOTP;
import com.auth.mfa.service.ServiceUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/totp") @RequiredArgsConstructor
public class ControllerTOTP {

    private final ServiceUser serviceUser;
    private final ServiceTOTP serviceTOTP;

    @GetMapping("")
    public String resetTOTP(@Valid @RequestBody User user) {
        return serviceUser.resetTOTP(user.getUsername());
    }
    @PostMapping("/{totpKey}")
    public boolean validateTOTP(@PathVariable int totpKey, @Valid @RequestBody User user) {
        return serviceTOTP.validateTOTP(user.getUsername(), totpKey);
    }
}