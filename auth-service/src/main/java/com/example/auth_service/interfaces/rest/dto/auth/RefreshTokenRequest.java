package com.example.auth_service.interfaces.rest.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank(message = "O refresh token não pode estar em branco")    
    String refreshToken
){}
