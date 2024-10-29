package com.auth.mfa.persistence.model;

import com.auth.mfa.persistence.GenericAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.Collection;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"})) @EqualsAndHashCode(callSuper = true)
public class Privilege extends GenericAuditEntity {

    private String name;

    @ManyToMany(mappedBy = "privilege")
    private Collection<Role> role;
}