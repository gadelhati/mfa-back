package com.auth.mfa.persistence.repository;

import com.auth.mfa.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryRole extends JpaRepository<Role, UUID>, RepositoryInterface<Role> {

}