package com.auth.mfa.persistence;

import com.auth.mfa.persistence.model.Role;
import com.auth.mfa.persistence.payload.request.DTORequestRole;
import com.auth.mfa.persistence.payload.response.DTOResponseRole;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MapperRole extends MapperInterface<Role, DTORequestRole, DTOResponseRole> {
    DTOResponseRole toDTO(Role entity);
    Role toObject(DTORequestRole dto);
}