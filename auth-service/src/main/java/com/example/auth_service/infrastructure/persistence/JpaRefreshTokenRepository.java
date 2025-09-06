package com.example.auth_service.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.example.auth_service.domain.Refresh.RefreshToken;
import com.example.auth_service.domain.Refresh.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

// Implementação JPA do repositório de RefreshToken.
@Repository
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    
    private final SpringDataRefreshTokenJpa jpa;

    /**
     * Construtor com injeção do repositório Spring Data JPA.
     * @param jpa repositório Spring Data para RefreshToken
     */
    public JpaRefreshTokenRepository(SpringDataRefreshTokenJpa jpa) {
        this.jpa = jpa;
    }

    /**
     * Salva um novo refresh token.
     * @param token RefreshToken a ser salvo
     * @return RefreshToken salvo
     */
    @Override
    public RefreshToken save(RefreshToken token) {
        return jpa.save(token);
    }

    /**
     * Busca um refresh token ativo pelo hash e data atual.
     * @param hash hash do token
     * @return Optional com RefreshToken ativo, se existir
     */
    @Override
    public Optional<RefreshToken> findActiveByHash(String hash) {
        return jpa.findByTokenHash(hash, Instant.now());
    }

    /**
     * Revoga um refresh token pelo seu UUID.
     * @param tokenId UUID do token a ser revogado
     */
    @Override
    public void revoke(UUID tokenId) {
        jpa.revoke(tokenId);
    }

    /**
     * Remove um refresh token pelo seu UUID.
     * @param TokenId UUID do token a ser removido
     */
    @Override
    public void deleteById(UUID TokenId) {
        jpa.deleteById(TokenId);
    }

     @Override
    public void revokeAllByUser(UUID userId) {
        jpa.revokeAllByUser(userId);
    }

}