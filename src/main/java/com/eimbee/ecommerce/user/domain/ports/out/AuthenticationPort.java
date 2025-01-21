package com.eimbee.ecommerce.user.domain.ports.out;

import com.eimbee.ecommerce.user.domain.model.User;

public interface AuthenticationPort {
    User authenticate(String email, String password);
}
