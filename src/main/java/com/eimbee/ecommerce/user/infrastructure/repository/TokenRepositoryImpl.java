package com.eimbee.ecommerce.user.infrastructure.repository;

import com.eimbee.ecommerce.user.domain.model.Token;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.out.TokenRepository;
import com.eimbee.ecommerce.user.infrastructure.mapper.TokenMapper;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.TokenEntity;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;
    private final TokenMapper mapper;

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenJpaRepository.findByToken(token).map(mapper::tokenEntityToTokenDomain);
    }

    @Override
    public List<Token> findAllValidTokensByUserId(UUID userId) {
        return tokenJpaRepository.findAllValidTokensByUserId(userId)
                .stream().map(mapper::tokenEntityToTokenDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void revokeAllUserTokensByUserId(UUID userId) {
        tokenJpaRepository.findAllValidTokensByUser(userId).forEach(token -> {
            token.setRevoked(true);
            tokenJpaRepository.save(token);
        });
    }

    @Override
    public void revokedTokenByToken(String token) {
        Optional<TokenEntity> tokenResult = tokenJpaRepository.findByToken(token);
        tokenResult.ifPresent(current -> {
            current.setRevoked(true);
            tokenJpaRepository.save(current);
        });
    }

    @Override
    public void save(String token, User user) {

        log.info("Este es el user: {}", user.toString());

        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now())
                .user(UserEntity.builder().id(user.getId()).build())
                .build();

        tokenJpaRepository.save(tokenEntity);
    }
}
