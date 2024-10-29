package com.auth.mfa.exception;

import lombok.RequiredArgsConstructor;
import lombok.Data;

@RequiredArgsConstructor @Data
public class ErrorObject {

    private final String field;
    private final String message;
}