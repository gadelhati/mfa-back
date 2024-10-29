package com.auth.mfa.persistence.repository;

import com.auth.mfa.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryPrivilege extends JpaRepository<Privilege, UUID>, RepositoryInterface<Privilege> {

}