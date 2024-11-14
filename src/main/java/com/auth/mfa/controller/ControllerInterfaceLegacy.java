package com.auth.mfa.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public interface ControllerInterfaceLegacy<T, S> {
    ResponseEntity<T> create(@RequestBody @Valid S created);
    ResponseEntity<Page<T>> retrieve(@RequestParam("value") String value, Pageable pageable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    ResponseEntity<T> update(@RequestBody @Valid S updated);
    ResponseEntity<T> delete(@PathVariable("id") UUID id);
    ResponseEntity<HttpStatus> delete();
}