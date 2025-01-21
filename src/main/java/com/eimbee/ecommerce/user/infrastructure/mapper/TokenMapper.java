package com.eimbee.ecommerce.user.infrastructure.mapper;

import com.eimbee.ecommerce.user.domain.model.Token;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.TokenEntity;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenEntity tokenDomainToTokenEntity(Token token) {
        return TokenEntity.builder()
                .id(token.getId())
                .token(token.getToken())
                .revoked(token.getRevoked())
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .user(UserEntity.builder().id(token.getUser().getId()).build())
                .build();
    }

    public Token tokenEntityToTokenDomain(TokenEntity token) {
        return Token.builder()
                .id(token.getId())
                .token(token.getToken())
                .revoked(token.getRevoked())
                .createdAt(token.getCreatedAt())
                .expiresAt(token.getExpiresAt())
                .user(User.builder().id(token.getUser().getId()).build())
                .build();
    }

}
