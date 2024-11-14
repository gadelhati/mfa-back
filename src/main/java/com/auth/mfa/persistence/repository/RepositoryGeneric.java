package com.auth.mfa.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public abstract interface RepositoryGeneric<T> extends JpaRepository<T, UUID> {
    T findByName(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String username, UUID id);
    boolean existsByName(String value);
    boolean existsByNameIgnoreCase(String value);
    Page<T> findById(Pageable pageable, UUID uuid);
    Page<T> findByIdOrderByIdAsc(Pageable pageable, UUID id);
    Page<T> findByNameContainingIgnoreCaseOrderByNameAsc(Pageable pageable, String name);
}