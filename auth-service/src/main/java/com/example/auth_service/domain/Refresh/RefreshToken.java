package com.example.auth_service.domain.Refresh;

import java.time.Instant;
import java.util.UUID;

import com.example.auth_service.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade RefreshToken.
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Valor do hash do token
    @Column(nullable = false, unique = true)
    private String tokenHash;

    // Data de expiração
    @Column(nullable = false)
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean revoked = false;

    // Construtor para facilitar a criação de novos tokens
    public RefreshToken(String tokenHash, Instant expiresAt, User user) {
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.user = user;
        this.revoked = false;
    }
   
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isActive() {
        return !isExpired();
    }

    public void revoke() {
        this.revoked = true;
    }

    public User getUser() {
        return user;
    }

    public UUID getId() {
        return id;
    }
}
