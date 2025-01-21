package com.eimbee.ecommerce.user.domain.ports.in;

import com.eimbee.ecommerce.user.application.dto.request.LoginRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;

public interface AuthenticationUserCase {
    JwtUserResponse authenticate(LoginRequest loginRequest);
    JwtUserResponse refreshToken(String refreshToken);
    void logout(String token);
}
