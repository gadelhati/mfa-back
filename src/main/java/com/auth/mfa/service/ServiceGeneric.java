package com.auth.mfa.service;

import com.auth.mfa.persistence.MapperInterface;
import com.auth.mfa.persistence.repository.RepositoryGeneric;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public abstract class ServiceGeneric<T, I, O> implements ServiceInterface<T, I, O> {

    private final RepositoryGeneric<T> repositoryGeneric;
    private final MapperInterface<T, I, O> mapperInterface;

    @Override
    public O create(I created){
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(created)));
    }
    @Override
    public Page<O> retrieve(Pageable pageable, String value, Class<T> entityClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T object = entityClass.getDeclaredConstructor().newInstance();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<T> example = Example.of(object, exampleMatcher);
            return repositoryGeneric.findAll(example, pageable).map(mapperInterface::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryGeneric.findById(pageable, UUID.fromString(value)).map(mapperInterface::toDTO);
        } catch (Exception e) {
            return repositoryGeneric.findAll(pageable).map(mapperInterface::toDTO);
        }
    }
    @Override
    public O update(UUID id, I updated){
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(updated)));
    }
    @Override
    public O delete(UUID id){
        O dtoResponse = mapperInterface.toDTO(repositoryGeneric.findById(id).orElse(null));
        repositoryGeneric.deleteById(id);
        return dtoResponse;
    }
    @Override
    public void delete() {
        repositoryGeneric.deleteAll();
    }

    public boolean existsByName(String value) {
        return repositoryGeneric.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        return repositoryGeneric.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}