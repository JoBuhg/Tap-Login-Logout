package com.example.auth_service.application.auth;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.auth_service.application.ports.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor   
public class LogoutHandler {
    private final RefreshTokenService refreshTokenService;

    public void handle(String refreshToken) {
        if(!refreshTokenService.validateRefreshToken(refreshToken)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    public void handleAllUserSessions(UUID userId) {
        refreshTokenService.revokeAllUserRefreshTokens(userId);
    }

}
