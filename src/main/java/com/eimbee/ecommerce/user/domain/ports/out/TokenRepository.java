package com.eimbee.ecommerce.user.domain.ports.out;

import com.eimbee.ecommerce.user.domain.model.Token;
import com.eimbee.ecommerce.user.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
    Optional<Token> findByToken(String token);

    List<Token> findAllValidTokensByUserId(UUID userId);

    void revokeAllUserTokensByUserId(UUID userId);

    void revokedTokenByToken(String token);

    void save(String token, User user);
}
