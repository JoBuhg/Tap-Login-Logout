package com.example.auth_service.domain.Refresh;

import java.util.Optional;
import java.util.UUID;


public interface RefreshTokenRepository {
    // Salva um novo refresh token
    RefreshToken save(RefreshToken token);

    // Busca um refresh token ativo pelo hash
    Optional<RefreshToken> findActiveByHash(String tokenHash);

    // Revoga um refresh token pelo ID
    void revoke(UUID tokenId);

    // Remove um refresh token pelo id
    void deleteById(UUID tokenId);

    void revokeAllByUser(UUID Userid);
}