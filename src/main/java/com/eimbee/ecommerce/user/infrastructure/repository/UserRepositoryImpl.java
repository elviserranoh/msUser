package com.eimbee.ecommerce.user.infrastructure.repository;

import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.out.UserRepository;
import com.eimbee.ecommerce.user.infrastructure.mapper.UserMapper;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserRoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    public List<User> list() {
        List<UserEntity> users = repository.findAll();
        return users.stream().map(mapper::UserEntityToUserDomain).toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        Optional<UserEntity> user = repository.findById(id);
        return user.map(mapper::UserEntityToUserDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = mapper.UserDomainToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(user.getRoles().stream().map(role -> roleJpaRepository.findByName(role.getName()).orElse(null)).collect(Collectors.toList()));
        return mapper.UserEntityToUserDomain(repository.save(userEntity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> user = repository.findByEmail(email);
        return user.map(mapper::UserEntityToUserDomain);
    }

}
