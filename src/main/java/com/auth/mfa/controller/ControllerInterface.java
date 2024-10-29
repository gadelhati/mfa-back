package com.auth.mfa.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

public interface ControllerInterface<T, S> {
    @ResponseBody T create(@RequestBody @Valid S created);
    @ResponseBody Page<T> retrieve(@RequestParam("value") String value, Pageable pageable);
    @ResponseBody T update(@RequestBody @Valid S updated);
    @ResponseBody T delete(@PathVariable("id") UUID id);
    @ResponseBody HttpStatus delete();
}