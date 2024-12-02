package com.auth.mfa.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public interface ControllerInterface<DTOResponse, DTORequest> {
    ResponseEntity<DTOResponse> create(@Valid DTORequest created);
    ResponseEntity<Page<DTOResponse>> retrieve(String value, Pageable pageable);
    ResponseEntity<DTOResponse> update(@Valid DTORequest updated);
    ResponseEntity<DTOResponse> deleteById(UUID id);
//    ResponseEntity<HttpStatus> deleteAll();
}