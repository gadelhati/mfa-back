package com.auth.mfa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "users") @NoArgsConstructor @AllArgsConstructor @Data
public class User {
    @Id
    private String username;
    private String password;
    private String secret;
}