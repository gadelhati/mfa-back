package com.auth.mfa.service;

import com.auth.mfa.MfaApplication;
import com.auth.mfa.persistence.MapStruct;
import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.payload.request.DTORequestUser;
import com.auth.mfa.persistence.payload.request.DTORequestUserPassword;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.persistence.repository.RepositoryRole;
import com.auth.mfa.persistence.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service @RequiredArgsConstructor
public class ServiceUser implements ServiceInterface<User, DTORequestUser, DTOResponseUser> {

    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final ServiceTOTP serviceTOTP;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);

    @Override
    public DTOResponseUser create(DTORequestUser created){
        LOGGER.info("Creating new entity.");
        User user = MapStruct.MAPPER.toObject(created);
        user.setPassword(passwordEncoder.encode(created.getPassword()));
        user.setSecret(serviceTOTP.generateSecret());
        user.setRole(Collections.singletonList(repositoryRole.findByName("ROLE_USER")));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override
    public Page<DTOResponseUser> retrieve(Pageable pageable, String value, Class<User> entityClass) {
        try {
            User object = new User();
            ExampleMatcher exampleMatcher = matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Method setMethod = object.getClass().getDeclaredMethod("set" + pageable.getSort().stream().findFirst()
                    .map(order -> StringUtils.capitalize(order.getProperty()))
                    .orElseThrow(() -> new IllegalArgumentException("No sorting property found.")), String.class);
            setMethod.invoke(object, value);
            Example<User> example = Example.of(object, exampleMatcher);
            return repositoryUser.findAll(example, pageable).map(MapStruct.MAPPER::toDTO);
        } catch (NoSuchMethodException exception) {
            LOGGER.warn("No setter method found for property.");
            return repositoryUser.findById(pageable, UUID.fromString(value)).map(MapStruct.MAPPER::toDTO);
        } catch (Exception e) {
            return repositoryUser.findAll(pageable).map(MapStruct.MAPPER::toDTO);
        }
    }
    @Override
    public DTOResponseUser update(UUID id, DTORequestUser updated){
        User user = MapStruct.MAPPER.toObject(updated);
        user.setPassword(Objects.requireNonNull(repositoryUser.findById(updated.getId()).orElse(null)).getPassword());
        LOGGER.info("Updating entity with ID: {}", id);
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override
    public DTOResponseUser delete(UUID id){
        DTOResponseUser dtoResponseUser = MapStruct.MAPPER.toDTO(repositoryUser.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity with ID " + id + " not found.")));
        LOGGER.info("Deleting entity with ID: {}", id);
        repositoryUser.deleteById(id);
        return dtoResponseUser;
    }
//    @Override
//    public void delete() {
//        repositoryUser.deleteAll();
//    }

    public boolean existsByName(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByUsernameIgnoreCase(value);
    }
    public boolean existsByNameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByUsernameIgnoreCaseAndIdNot(value, id);
    }
    public boolean existsByEmail(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByEmailIgnoreCase(value);
    }
    public boolean existsByEmailAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByEmailIgnoreCaseAndIdNot(value, id);
    }

    public DTOResponseUser changePassword(DTORequestUserPassword updated){
        User user = repositoryUser.findById(updated.getId()).orElse(null);
//        try {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof UserDetails) {
//                LOGGER.info(((UserDetails) principal).getUsername());
//            }
//        } catch (Exception e){
//
//        }
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.getPassword()));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    public String resetTOTP(String userName) {
        User user = repositoryUser.findByUsername(userName).orElse(null);
        Objects.requireNonNull(user).setSecret(serviceTOTP.generateSecret());
        repositoryUser.save(user);
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", userName, userName + "@auth.com", user.getSecret(), env.getRequiredProperty("application.name"));
    }
}