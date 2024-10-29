package com.auth.mfa.persistence;

import com.auth.mfa.persistence.model.*;
import com.auth.mfa.persistence.payload.request.DTORequestPrivilege;
import com.auth.mfa.persistence.payload.request.DTORequestRole;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.response.DTOResponsePrivilege;
import com.auth.mfa.persistence.payload.response.DTOResponseRole;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface MapStruct {

    MapStruct MAPPER = Mappers.getMapper(MapStruct.class);
    DTOResponsePrivilege toDTO(Privilege privilege);
    DTOResponseRole toDTO(Role role);
    DTOResponseUser toDTO(User user);

    Privilege toObject(DTORequestPrivilege dtoRequestPrivilege);
    Role toObject(DTORequestRole dtoRequestRole);
    User toObject(DTORequestUser dtoRequestUser);
}