package com.auth.mfa.persistence;

import com.auth.mfa.persistence.model.Privilege;
import com.auth.mfa.persistence.payload.request.DTORequestPrivilege;
import com.auth.mfa.persistence.payload.response.DTOResponsePrivilege;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MapperPrivilege extends MapperInterface<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {
    DTOResponsePrivilege toDTO(Privilege entity);
    Privilege toObject(DTORequestPrivilege dto);
}