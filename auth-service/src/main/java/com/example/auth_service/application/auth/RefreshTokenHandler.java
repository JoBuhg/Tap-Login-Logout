package com.example.auth_service.application.auth;

import com.example.auth_service.application.ports.RefreshTokenService;
import com.example.auth_service.application.ports.TokenService;
import com.example.auth_service.domain.user.User;
import com.example.auth_service.interfaces.rest.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RefreshTokenHandler {
    
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    
    public TokenResponse handle(String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
        
        User user = refreshTokenService.getUserFromRefreshToken(refreshToken);
        
        // Revoke the old refresh token
        refreshTokenService.revokeRefreshToken(refreshToken);
        
        // Generate new token pair
        TokenService.TokenPair pair = tokenService.issue(user);
        return new TokenResponse(pair.token(), pair.refreshToken(), pair.expiresIn());
    }
}
