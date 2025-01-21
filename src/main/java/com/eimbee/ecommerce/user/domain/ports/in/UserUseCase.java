package com.eimbee.ecommerce.user.domain.ports.in;

import com.eimbee.ecommerce.user.application.dto.request.CreateUserRequest;
import com.eimbee.ecommerce.user.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserUseCase {

    List<User> list();
    User findById(UUID id);
    User findByEmail(String email);
    User save(CreateUserRequest user);
    User edit(User user, UUID id);
    void disableById(UUID id);
    void enableById(UUID id);
}
