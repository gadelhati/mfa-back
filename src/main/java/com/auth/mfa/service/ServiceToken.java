package com.auth.mfa.service;

import com.auth.mfa.persistence.MapStruct;
import com.auth.mfa.persistence.model.Token;
import com.auth.mfa.persistence.payload.request.DTORequestToken;
import com.auth.mfa.persistence.payload.response.DTOResponseToken;
import com.auth.mfa.persistence.repository.RepositoryToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public class ServiceToken implements ServiceInterface<Token,DTORequestToken, DTOResponseToken> {

    private final RepositoryToken repositoryToken;

    @Override
    public DTOResponseToken create(DTORequestToken created){
        return MapStruct.MAPPER.toDTO(repositoryToken.save(MapStruct.MAPPER.toObject(created)));
    }
    @Override
    public Page<DTOResponseToken> retrieve(Pageable pageable, String value, Class<Token> entityClass) {
        Token object = new Token();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<Token> example = Example.of(object, exampleMatcher);
            return repositoryToken.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryToken.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryToken.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseToken update(UUID id, DTORequestToken updated){
        Token token = MapStruct.MAPPER.toObject(updated);
        return MapStruct.MAPPER.toDTO(repositoryToken.save(token));
    }
    @Override
    public DTOResponseToken delete(UUID id){
        DTOResponseToken dtoResponseToken = MapStruct.MAPPER.toDTO(repositoryToken.findById(id).orElse(null));
        repositoryToken.deleteById(id);
        return dtoResponseToken;
    }
    @Override
    public void delete() {
        repositoryToken.deleteAll();
    }
}