package com.eimbee.ecommerce.user.domain.ports.out;

import com.eimbee.ecommerce.user.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> list();

    Optional<User> findById(UUID id);

    User save(User user);

    Optional<User> findByEmail(String email);


}

