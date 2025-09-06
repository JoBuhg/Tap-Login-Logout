package com.example.auth_service.infrastructure.security;

import com.example.auth_service.application.ports.PasswordHasher;
import com.example.auth_service.application.ports.RefreshTokenService;
import com.example.auth_service.domain.Refresh.RefreshToken;
import com.example.auth_service.domain.Refresh.RefreshTokenRepository;
import com.example.auth_service.domain.user.User;
import com.example.auth_service.infrastructure.config.JwtProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordHasher passwordHasher;
    private final JwtProperties jwtProperties;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    @Transactional
    public String generateRefreshToken(User user) {
        // Revoke existing refresh tokens for the user
        refreshTokenRepository.revokeAllByUser(user.getId());

        // Generate a new random token
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Hash the token for storage
        String tokenHash = passwordHasher.hash(token);

        // Set expiration time (default 7 days)
        Instant expiresAt = Instant.now().plusSeconds(jwtProperties.getRefreshTtlSeconds());

        // Create and save refresh token
        RefreshToken refreshToken = new RefreshToken(tokenHash, expiresAt, user);
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        String tokenHash = passwordHasher.hash(token);
        return refreshTokenRepository.findActiveByHash(tokenHash)
                .map(RefreshToken::isActive)
                .orElse(false);
    }

    @Override
    public User getUserFromRefreshToken(String token) {
        String tokenHash = passwordHasher.hash(token);
        return refreshTokenRepository.findActiveByHash(tokenHash)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }

    @Override
    public void revokeRefreshToken(String token) {
        String tokenHash = passwordHasher.hash(token);
        refreshTokenRepository.findActiveByHash(tokenHash)
                .ifPresent(refreshToken -> refreshTokenRepository.revoke(refreshToken.getId()));
    }

    @Override
    public void revokeAllUserRefreshTokens(UUID userId) {
        refreshTokenRepository.revokeAllByUser(userId);
    }
}
