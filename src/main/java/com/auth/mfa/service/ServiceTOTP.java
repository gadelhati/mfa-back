package com.auth.mfa.service;

import com.auth.mfa.exception.MissingTOTPKeyAuthenticatorException;
import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service @RequiredArgsConstructor
public class ServiceTOTP {

    private final RepositoryUser repositoryUser;
    private final Environment env;

    public boolean validateTOTP(String userName, Integer totpKey) {
        User user = repositoryUser.findByUsername(userName).get();
        String secret = user.getSecret();
        if (StringUtils.hasText(secret)) {
            if (totpKey != null) {
                try {
                    if (!verifyCode(secret, totpKey, Integer.parseInt(env.getRequiredProperty("application.time")))) {
                        System.out.printf("Code %d was not valid", totpKey);
                        throw new BadCredentialsException("Invalid TOTP code");
                    }
                } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new InternalAuthenticationServiceException("TOTP code verification failed", e);
                }
            } else {
                throw new MissingTOTPKeyAuthenticatorException("TOTP code is mandatory");
            }
        }
        return true;
    }
    public String generateSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }
    public boolean verifyCode(String secret, int code, int variance)
            throws InvalidKeyException, NoSuchAlgorithmException {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(secret);
        for (int i = -variance; i <= variance; i++) {
            long calculatedCode = getCode(secretBytes, timeIndex + i);
            if (calculatedCode == code) {
                return true;
            }
        }
        return false;
    }
    private long getCode(byte[] secret, long timeIndex)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash % 1000000;
    }
}