package com.auth.mfa.persistence;

public interface MapperInterface<T, I, O> {
    O toDTO(T entity);
    T toObject(I dto);
}