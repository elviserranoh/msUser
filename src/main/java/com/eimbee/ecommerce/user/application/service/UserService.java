package com.eimbee.ecommerce.user.application.service;

import com.eimbee.ecommerce.user.application.dto.request.CreateUserRequest;
import com.eimbee.ecommerce.user.application.mappers.UserDataMapper;
import com.eimbee.ecommerce.user.domain.exception.AlreadyDisableUserException;
import com.eimbee.ecommerce.user.domain.exception.AlreadyEnableUserException;
import com.eimbee.ecommerce.user.domain.exception.UnknownUserException;
import com.eimbee.ecommerce.user.domain.exception.UserEmailAlreadyExistsException;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import com.eimbee.ecommerce.user.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository repository;
    private final UserDataMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return repository.list();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UnknownUserException(String.format("User Id not found: %s", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UnknownUserException(email + " no se encuentra"));
    }

    @Override
    @Transactional
    public User save(CreateUserRequest request) {
        existsEmail(request.getEmail());
        return repository.save(mapper.UserRequestToUserDomain(request));
    }

    @Override
    @Transactional
    public User edit(User user, UUID id) {
        User item = this.findById(id);
        item.setName(user.getName());
        item.setEmail(user.getEmail());
        item.setPassword(user.getPassword());
        item.setPhones(user.getPhones());
        return repository.save(item);
    }

    @Override
    @Transactional
    public void disableById(UUID id) {
        User user = this.findById(id);

        verifyUserIsEnabled(user);

        user.setIsActive(false);
        repository.save(user);
    }

    @Override
    @Transactional
    public void enableById(UUID id) {

        User user = this.findById(id);

        verifyUserIsDisabled(user);

        user.setIsActive(true);
        repository.save(user);
    }

    private void existsEmail(String email) {
        Optional<User> userResult = repository.findByEmail(email);
        if (userResult.isPresent()) {
            throw new UserEmailAlreadyExistsException(String.format("User email exists: %s", email));
        }
    }

    private static void verifyUserIsEnabled(User user) {
        if (!user.getIsActive()) {
            throw new AlreadyDisableUserException(String.format("User id already disabled: %s", user.getId()));
        }
    }

    private void verifyUserIsDisabled(User user) {
        if (!user.getIsActive()) {
            throw new AlreadyEnableUserException(String.format("User id already enabled: %s", user.getId()));
        }
    }

}
