package com.auth.mfa.controller;

import com.auth.mfa.MfaApplication;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.service.ServiceUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/user") @RequiredArgsConstructor
public class ControllerUser {

    private final ServiceUser serviceUser;
    private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);

    @PostMapping("")
    public @ResponseBody DTOResponseUser create(@Valid @RequestBody DTORequestUser dtoRequestUser) {
        LOGGER.info("An INFO Message "+dtoRequestUser.getUsername()+" CREATE AN USER");
        return serviceUser.create(dtoRequestUser);
    }
}