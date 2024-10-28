package com.auth.mfa.service;

import com.auth.mfa.model.User;
import com.auth.mfa.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class ServiceUser {

    private final RepositoryUser repositoryUser;
    private final ServiceTOTP serviceTOTP;
    private final Environment env;

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    public User create(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        user.setSecret(serviceTOTP.generateSecret());
        return repositoryUser.save(user);
    }
    public String resetTOTP(String userName) {
        User user = repositoryUser.findById(userName).get();
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", userName, userName + "@auth.com", user.getSecret(), env.getRequiredProperty("mfa.application.name"));
    }
}