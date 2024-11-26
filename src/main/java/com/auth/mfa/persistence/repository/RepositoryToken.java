package com.auth.mfa.persistence.repository;

import com.auth.mfa.persistence.model.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryToken extends JpaRepository<Token, UUID> {

    Page<Token> findById(Pageable pageable, UUID uuid);
    Optional<Token> findByRefreshToken(UUID uuid);
    boolean existsByRefreshToken(UUID refreshToken);
}