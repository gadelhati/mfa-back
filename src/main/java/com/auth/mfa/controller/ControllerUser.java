package com.auth.mfa.controller;

import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.service.ServiceUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/user") @RequiredArgsConstructor
public class ControllerUser {

    private final ServiceUser serviceUser;

    @PostMapping("")
    public @ResponseBody DTOResponseUser create(@Valid @RequestBody DTORequestUser dtoRequestUser) {
        return serviceUser.create(dtoRequestUser);
    }
}