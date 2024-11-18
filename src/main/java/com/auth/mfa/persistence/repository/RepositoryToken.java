package com.auth.mfa.persistence.repository;

import com.auth.mfa.persistence.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryToken extends JpaRepository<Token, UUID> {

    Page<Token> findById(Pageable pageable, UUID uuid);
    boolean existsByRefreshToken(UUID refreshToken);
    void deleteByRefreshToken(UUID refreshToken);
}