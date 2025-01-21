package com.eimbee.ecommerce.user.application.service;

import com.eimbee.ecommerce.user.domain.exception.TokenGenerationException;
import com.eimbee.ecommerce.user.domain.exception.TokenRevocationException;
import com.eimbee.ecommerce.user.domain.exception.TokenValidationException;
import com.eimbee.ecommerce.user.domain.model.Token;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.TokenProvider;
import com.eimbee.ecommerce.user.domain.ports.in.TokenUseCase;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import com.eimbee.ecommerce.user.domain.ports.out.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements TokenUseCase {

    private final UserUseCase userUseCase;
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public String generateAccessToken(User user) {
        try {
            User currentUser = userUseCase.findByEmail(user.getEmail());
            String token = tokenProvider.generateToken(currentUser);
            tokenRepository.save(token, currentUser);
            return token;
        } catch (Exception e) {
            log.error("Error generating access token for user {}: {}", user.getId(), e.getMessage());
            throw new TokenGenerationException("Error generating access token", e);
        }
    }

    @Override
    @Transactional
    public String generateRefreshToken(User user) {
        try {
            User currentUser = userUseCase.findByEmail(user.getEmail());
            String token = tokenProvider.generateRefreshToken(currentUser);
            tokenRepository.save(token, currentUser);
            return token;
        } catch (Exception e) {
            log.error("Error generating refresh token for user {}: {}", user.getId(), e.getMessage());
            throw new TokenGenerationException("Error generating refresh token", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean validateToken(String token, User user) {

        try {
            if (token == null || user == null) {
                return false;
            }

            Optional<Token> tokenEntity = tokenRepository.findByToken(token);
            if (tokenEntity.isEmpty() || tokenEntity.get().getRevoked()) {
                return false;
            }

            String userEmail = tokenProvider.extractUserEmail(token);
            boolean isExpired = tokenProvider.isTokenExpired(token);

            return userEmail.equals(user.getEmail()) && !isExpired;
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public UUID extractUserId(String token) {
        try {
            return tokenProvider.extractUserId(token);
        } catch (Exception e) {
            log.error("Error extracting user ID from token: {}", e.getMessage());
            throw new TokenValidationException("Error extracting user ID from token", e);
        }
    }

    @Override
    public String extractUserEmail(String token) {
        try {
            return tokenProvider.extractUserEmail(token);
        } catch (Exception e) {
            log.error("Error extracting user email from token: {}", e.getMessage());
            throw new TokenValidationException("Error extracting user email from token", e);
        }
    }

    @Override
    @Transactional
    public void revokeAllUserTokens(User user) {
        try {
            tokenRepository.revokeAllUserTokensByUserId(user.getId());
        } catch (Exception e) {
            log.error("Error revoking all tokens for user {}: {}", user.getId(), e.getMessage());
            throw new TokenRevocationException("Error revoking all user tokens", e);
        }
    }

    @Override
    @Transactional
    public void revokeToken(String token) {
        try {
            tokenRepository.revokedTokenByToken(token);
        } catch (Exception e) {
            log.error("Error revoking token: {}", e.getMessage());
            throw new TokenRevocationException("Error revoking token", e);
        }
    }
}
