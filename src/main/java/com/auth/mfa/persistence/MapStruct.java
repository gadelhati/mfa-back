package com.auth.mfa.persistence;

import com.auth.mfa.persistence.model.*;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface MapStruct {

    MapStruct MAPPER = Mappers.getMapper(MapStruct.class);
    DTOResponseUser toDTO(User user);
    DTOResponseToken toDTO(Token token);

    User toObject(DTORequestUser dtoRequestUser);
    Token toObject(DTORequestToken dtoRequestToken);
}