package com.example.auth_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.auth_service.domain.Refresh.RefreshToken;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshToken, UUID> {
    
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.tokenHash = :tokenHash")
    void revokeByTokenHash(@Param("tokenHash") String tokenHash);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.id = :id")
    int revoke(@Param("id") UUID id);

    @Query("SELECT r FROM RefreshToken r WHERE r.tokenHash = :tokenHash AND r.expiresAt = :expiresAt")
    Optional<RefreshToken> findByTokenHash(@Param("tokenHash") String tokenHash, @Param("expiresAt") Instant expiresAt);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user.id = :userId")
    void revokeAllByUser(@Param("userId") UUID userId);
}
