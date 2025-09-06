package com.example.auth_service.interfaces.rest;

import com.example.auth_service.application.auth.LogoutHandler;
import com.example.auth_service.application.auth.PasswordLoginHandler;
import com.example.auth_service.application.auth.RefreshTokenHandler;
import com.example.auth_service.interfaces.rest.dto.auth.LogoutRequest;
import com.example.auth_service.interfaces.rest.dto.auth.PasswordLoginRequest;
import com.example.auth_service.interfaces.rest.dto.auth.RefreshTokenRequest;
import com.example.auth_service.interfaces.rest.dto.auth.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints para autenticação e gerenciamento de tokens")

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    
    private final PasswordLoginHandler passwordLoginHandler;
    private final RefreshTokenHandler refreshTokenHandler;
    private final LogoutHandler logoutHandler;

    @Operation(summary = "Login com email e senha", description = "Autentica um usuário e retorna tokens de acesso e refresh")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginWithPassword(@Valid @RequestBody PasswordLoginRequest request) {
        TokenResponse token = passwordLoginHandler.handle(request.email(), request.password());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Refresh token", description = "Gera um novo token de acesso usando o refresh token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado")
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse token = refreshTokenHandler.handle(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Logout", description = "Revoga o refresh token, efetivando o logout")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        logoutHandler.handle(request.refreshToken());
        return ResponseEntity.ok().build();
    }
}
