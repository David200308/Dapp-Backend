package com.skyproton.backend.schemas.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AuthSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authId;

    @Getter
    @Column(unique = true, nullable = false)
    private String authUuid;

    @Setter
    @Getter
    @Column(nullable = false)
    private String walletAddress;

    @Getter
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Column(nullable = false)
    private Boolean isVerified;

    @Getter
    private LocalDateTime verifiedAt;

    public AuthSchema() {
        this.authUuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.isVerified = false;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
        if (isVerified != null && isVerified) {
            this.verifiedAt = LocalDateTime.now();
        }
    }

}
