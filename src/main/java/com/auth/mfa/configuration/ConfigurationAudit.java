package com.auth.mfa.configuration;

import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.repository.RepositoryUser;
import com.auth.mfa.service.ServiceAuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration @EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ConfigurationAudit {
    private final RepositoryUser repositoryUser;

    public ConfigurationAudit(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }
    @Bean
    public AuditorAware<User> auditorAware() {
        return new ServiceAuditorAwareImpl(repositoryUser);
    }
}