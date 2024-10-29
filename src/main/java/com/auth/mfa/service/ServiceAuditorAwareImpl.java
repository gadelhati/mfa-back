package com.auth.mfa.service;

import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.repository.RepositoryUser;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceAuditorAwareImpl implements AuditorAware<User> {

    private final RepositoryUser repositoryUser;

    public ServiceAuditorAwareImpl(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }
    @Override @NonNull
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            return Optional.empty();
        }
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return repositoryUser.findByUsername(username);
    }
}