package com.auth.mfa.service;

import com.auth.mfa.persistence.MapStruct;
import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.persistence.repository.RepositoryRole;
import com.auth.mfa.persistence.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public class ServiceUser implements ServiceInterface<DTOResponseUser, DTORequestUser> {

    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final ServiceTOTP serviceTOTP;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DTOResponseUser create(DTORequestUser created){
        User user = MapStruct.MAPPER.toObject(created);
        user.setPassword(passwordEncoder.encode(created.getPassword()));
        user.setSecret(serviceTOTP.generateSecret());
        user.setRole(Collections.singletonList(repositoryRole.findByName("ROLE_USER")));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override
    public Page<DTOResponseUser> retrieve(Pageable pageable, String value) {
        User object = new User();
        ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtils.capitalize(pageable.getSort().stream().findFirst().get().getProperty()), String.class);
            setMethod.invoke(object, value);
            Example<User> example = Example.of(object, exampleMatcher);
            return repositoryUser.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            return repositoryUser.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryUser.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseUser update(UUID id, DTORequestUser updated){
        User user = MapStruct.MAPPER.toObject(updated);
        user.setPassword(Objects.requireNonNull(repositoryUser.findById(updated.getId()).orElse(null)).getPassword());
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override
    public DTOResponseUser delete(UUID id){
        DTOResponseUser dtoResponseUser = MapStruct.MAPPER.toDTO(repositoryUser.findById(id).orElse(null));
        repositoryUser.deleteById(id);
        return dtoResponseUser;
    }
    @Override
    public void delete() {
        repositoryUser.deleteAll();
    }

    public boolean existsByName(String value) {
        return repositoryUser.existsByUsernameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        return repositoryUser.existsByUsernameIgnoreCaseAndIdNot(value, id);
    }
    public boolean existsByEmail(String value) {
        return repositoryUser.existsByEmailIgnoreCase(value);
    }
    public boolean existsByEmailAndIdNot(String value, UUID id) {
        return repositoryUser.existsByEmailIgnoreCaseAndIdNot(value, id);
    }

    public DTOResponseUser changePassword(DTORequestUser updated){
        User user = repositoryUser.findById(updated.getId()).orElse(null);
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.getPassword()));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    public String resetTOTP(String userName) {
        User user = repositoryUser.findByUsername(userName).get();
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", userName, userName + "@auth.com", user.getSecret(), env.getRequiredProperty("mfa.application.name"));
    }
}