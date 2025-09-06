package com.example.auth_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.auth_service.domain.Refresh.RefreshToken;

import java.util.Optional;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHashAndActiveTrue(String tokenHash);

    @Modifying
    @Query("update TokenRefresh t set t.active = false where t.tokenHash = :tokenHash")
    void revokeByTokenHash(String tokenHash);
}
