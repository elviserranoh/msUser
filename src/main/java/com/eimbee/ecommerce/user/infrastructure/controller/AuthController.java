package com.eimbee.ecommerce.user.infrastructure.controller;

import com.eimbee.ecommerce.user.application.dto.request.LoginRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;
import com.eimbee.ecommerce.user.domain.ports.in.AuthenticationUserCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationUserCase authService;

    @PostMapping
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.authenticate(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtUserResponse> refresh(@RequestHeader("Authentication") String refreshToken) {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.refreshToken(refreshToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authentication") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authService.logout(token);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
