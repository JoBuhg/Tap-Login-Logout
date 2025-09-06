package com.example.auth_service.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.example.auth_service.domain.Refresh.RefreshToken;
import com.example.auth_service.domain.Refresh.RefreshTokenRepository;

import java.util.Optional;

@Repository
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    private final SpringDataRefreshTokenJpa jpa;

    public JpaRefreshTokenRepository(SpringDataRefreshTokenJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        return jpa.save(token);
    }

    @Override
    public Optional<RefreshToken> findActiveByHash(String hash) {
        return jpa.findByTokenHashAndActiveTrue(hash);
    }

    @Override
    public void revoke(String hash) {
        jpa.revokeByTokenHash(hash);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

}