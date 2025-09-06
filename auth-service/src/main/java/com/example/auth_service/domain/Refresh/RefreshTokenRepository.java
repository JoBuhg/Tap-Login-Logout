package com.example.auth_service.domain.Refresh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface RefreshTokenRepository {
    RefreshToken refreshToken = new RefreshToken();
    // Salva um novo refresh token
    RefreshToken save(RefreshToken token);

    // Busca um refresh token ativo pelo hash
    Optional<RefreshToken> findActiveByHash(String hash);

    // Revoga um refresh token pelo hash
    void revoke(String hash);

    // Remove um refresh token pelo id
    void deleteById(Long id);


}