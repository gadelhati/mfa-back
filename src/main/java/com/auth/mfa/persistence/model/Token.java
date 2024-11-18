package com.auth.mfa.persistence.model;

import com.auth.mfa.persistence.GenericAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Audited @Entity @Data @AllArgsConstructor @NoArgsConstructor @Table @EqualsAndHashCode(callSuper = true)
public class Token extends GenericAuditEntity {

    private UUID refreshToken;
    private boolean active;
}