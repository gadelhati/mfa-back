package com.auth.mfa.service;

import com.auth.mfa.persistence.MapperInterface;
import com.auth.mfa.persistence.model.Role;
import com.auth.mfa.persistence.payload.request.DTORequestRole;
import com.auth.mfa.persistence.payload.response.DTOResponseRole;
import com.auth.mfa.persistence.repository.RepositoryGeneric;
import com.auth.mfa.persistence.repository.RepositoryRole;
import org.springframework.stereotype.Service;

@Service
public class ServiceRole extends ServiceGeneric<Role, DTORequestRole, DTOResponseRole> {
    private final RepositoryRole repositoryRole;
    private final MapperInterface<Role, DTORequestRole, DTOResponseRole> mapperInterface;

    public ServiceRole(RepositoryGeneric<Role> repositoryGeneric, MapperInterface<Role, DTORequestRole, DTOResponseRole> mapperInterface, RepositoryRole repositoryRole) {
        super(repositoryGeneric, mapperInterface);
        this.repositoryRole = repositoryRole;
        this.mapperInterface = mapperInterface;
    }
}