package com.auth.mfa.service;

import com.auth.mfa.persistence.MapperInterface;
import com.auth.mfa.persistence.model.Privilege;
import com.auth.mfa.persistence.payload.request.DTORequestPrivilege;
import com.auth.mfa.persistence.payload.response.DTOResponsePrivilege;
import com.auth.mfa.persistence.repository.RepositoryGeneric;
import com.auth.mfa.persistence.repository.RepositoryPrivilege;
import org.springframework.stereotype.Service;

@Service
public class ServicePrivilege extends ServiceGeneric<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {
    private final RepositoryPrivilege repositoryPrivilege;
    private final MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface;

    public ServicePrivilege(RepositoryGeneric<Privilege> repositoryGeneric, MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> mapperInterface, RepositoryPrivilege repositoryPrivilege) {
        super(repositoryGeneric, mapperInterface);
        this.repositoryPrivilege = repositoryPrivilege;
        this.mapperInterface = mapperInterface;
    }
}