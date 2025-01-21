package com.eimbee.ecommerce.user.application.service;

import com.eimbee.ecommerce.user.application.dto.request.LoginRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;
import com.eimbee.ecommerce.user.application.mappers.UserDataMapper;
import com.eimbee.ecommerce.user.domain.exception.InvalidTokenException;
import com.eimbee.ecommerce.user.domain.exception.UnAuthorizedDomainException;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.AuthenticationUserCase;
import com.eimbee.ecommerce.user.domain.ports.in.TokenUseCase;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import com.eimbee.ecommerce.user.domain.ports.out.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.eimbee.ecommerce.user.infrastructure.security.JwtTokenProvider.TOKEN_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticationUserCase {

    private final AuthenticationPort authenticationPort;
    private final TokenUseCase tokenUseCase;
    private final UserUseCase userService;
    private final UserDataMapper mapper;

    @Override
    public JwtUserResponse authenticate(LoginRequest loginRequest) {

        User authenticatedUser = authenticationPort.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        User currentUser = userService.findByEmail(authenticatedUser.getEmail());

        String jwtToken = tokenUseCase.generateAccessToken(currentUser);
        String jwtRefreshToken = tokenUseCase.generateRefreshToken(currentUser);

        return mapper.userDomainAndTokenToJwtUserResponse(currentUser, jwtToken, jwtRefreshToken);
    }

    @Override
    public JwtUserResponse refreshToken(String refreshToken) {

        final String tokenValue = extractToken(refreshToken);
        UUID userId = tokenUseCase.extractUserId(tokenValue);

        User user = userService.findById(userId);

        if (!tokenUseCase.validateToken(tokenValue, user)) {
            throw new UnAuthorizedDomainException("Token inválido o expirado");
        }

        tokenUseCase.revokeAllUserTokens(user);

        String jwtToken = tokenUseCase.generateAccessToken(user);
        String jwtRefreshToken = tokenUseCase.generateRefreshToken(user);

        return mapper.userDomainAndTokenToJwtUserResponse(user, jwtToken, jwtRefreshToken);

    }

    @Override
    public void logout(String token) {
        tokenUseCase.revokeToken(extractToken(token));
    }

    private String extractToken(String token) {
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            throw new InvalidTokenException("Token format invalid");
        }
        return token.substring(TOKEN_PREFIX.length());
    }

}
