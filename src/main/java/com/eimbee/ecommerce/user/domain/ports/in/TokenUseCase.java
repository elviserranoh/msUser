package com.eimbee.ecommerce.user.domain.ports.in;

import com.eimbee.ecommerce.user.domain.model.User;

import java.util.UUID;

public interface TokenUseCase {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    Boolean validateToken(String token, User user);

    UUID extractUserId(String token);

    String extractUserEmail(String token);

    void revokeAllUserTokens(User user);

    void revokeToken(String token);
}
