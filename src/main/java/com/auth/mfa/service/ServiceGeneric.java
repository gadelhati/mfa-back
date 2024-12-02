package com.auth.mfa.service;

import com.auth.mfa.MfaApplication;
import com.auth.mfa.persistence.MapperInterface;
import com.auth.mfa.persistence.repository.RepositoryGeneric;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class ServiceGeneric<T, DTORequest, DTOResponse> implements ServiceInterface<T, DTORequest, DTOResponse> {

    private final RepositoryGeneric<T> repositoryGeneric;
    private final MapperInterface<T, DTORequest, DTOResponse> mapperInterface;
    private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);

    @Override
    public DTOResponse create(DTORequest created){
        LOGGER.info("Creating new entity.");
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(created)));
    }
    @Override
    public Page<DTOResponse> retrieve(Pageable pageable, String value, Class<T> entityClass) {
        try {
            T object = entityClass.getDeclaredConstructor().newInstance();
            ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Method setMethod = object.getClass().getDeclaredMethod("set" + pageable.getSort().stream().findFirst()
                    .map(order -> StringUtils.capitalize(order.getProperty()))
                    .orElseThrow(() -> new IllegalArgumentException("No sorting property found.")), String.class);
            setMethod.invoke(object, value);
            Example<T> example = Example.of(object, exampleMatcher);
            return repositoryGeneric.findAll(example, pageable).map(mapperInterface::toDTO);
        } catch (NoSuchMethodException exception) {
            LOGGER.warn("No setter method found for property.");
            return repositoryGeneric.findById(pageable, UUID.fromString(value)).map(mapperInterface::toDTO);
        } catch (Exception e) {
            return repositoryGeneric.findAll(pageable).map(mapperInterface::toDTO);
        }
    }
    @Override
    public DTOResponse update(UUID id, DTORequest updated){
        T existingEntity = repositoryGeneric.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with ID " + id + " not found."));
        LOGGER.info("Updating entity with ID: {}", id);
        return mapperInterface.toDTO(repositoryGeneric.save(mapperInterface.toObject(updated)));
    }
    @Override
    public DTOResponse delete(UUID id){
        DTOResponse dtoResponse = mapperInterface.toDTO(repositoryGeneric.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity with ID " + id + " not found.")));
        LOGGER.info("Deleting entity with ID: {}", id);
        repositoryGeneric.deleteById(id);
        return dtoResponse;
    }
//    @Override
//    public void delete() {
//        LOGGER.info("Deleting all entities");
//        repositoryGeneric.deleteAll();
//    }

    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryGeneric.existsByNameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryGeneric.existsByNameIgnoreCaseAndIdNot(value, id);
    }
}