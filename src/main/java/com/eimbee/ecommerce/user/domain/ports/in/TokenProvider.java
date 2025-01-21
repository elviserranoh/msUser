package com.eimbee.ecommerce.user.domain.ports.in;

import com.eimbee.ecommerce.user.domain.model.User;

import java.util.Date;
import java.util.UUID;

public interface TokenProvider {
    String generateToken(User user);

    String generateRefreshToken(User user);

    UUID extractUserId(String token);

    String extractUserEmail(String token);

    Boolean isTokenExpired(String token);

    Date getExpirationDate(String token);

}
