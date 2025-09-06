package com.example.auth_service.application.ports;

import java.util.UUID;
import com.example.auth_service.domain.user.User;

public interface RefreshTokenService {
    
    String generateRefreshToken(User user);
    
    boolean validateRefreshToken(String token);
    
    User getUserFromRefreshToken(String token);
    
    void revokeRefreshToken(String token);
    
    void revokeAllUserRefreshTokens(UUID userId);
}
