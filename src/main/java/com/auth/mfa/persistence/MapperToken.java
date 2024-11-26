package com.auth.mfa.persistence;

import com.auth.mfa.persistence.model.Token;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MapperToken extends MapperInterface<Token, DTORequestToken, DTOResponseToken> {
    DTOResponseToken toDTO(Token entity);
    Token toObject(DTORequestToken dto);
}