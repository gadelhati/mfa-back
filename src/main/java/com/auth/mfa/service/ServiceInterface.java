package com.auth.mfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public interface ServiceInterface<T, I, O> {
    O create(I created);
    Page<O> retrieve(Pageable pageable, String value, Class<T> entityClass);
    O update(UUID id, I updated);
    O delete(UUID id);
//    void delete();
}