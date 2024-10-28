package com.auth.mfa.controller;

import com.auth.mfa.model.User;
import com.auth.mfa.service.ServiceUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/user") @AllArgsConstructor
public class ControllerUser {

    private final ServiceUser serviceUser;

    @PostMapping("")
    public @ResponseBody User create(@RequestBody User user) {
        User savedUser = serviceUser.create(user);
        savedUser.setPassword("");
        return savedUser;
    }
}