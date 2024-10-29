package com.auth.mfa.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders httpHeaders, @NonNull HttpStatusCode httpStatusCode, @NonNull WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatusCode, Arrays.toString(exception.getStackTrace()), exception.getAllErrors().toString());
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError((error).getField(), error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errorResponse.addValidationError((error).getObjectName(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}