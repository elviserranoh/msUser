package com.eimbee.ecommerce.user.infrastructure.controller;

import com.eimbee.ecommerce.user.application.dto.request.CreateUserRequest;
import com.eimbee.ecommerce.user.application.dto.request.LoginRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.AuthenticationUserCase;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase service;
    private final AuthenticationUserCase authUseCase;

    @GetMapping(headers = {"Authorization"})
    public ResponseEntity<List<User>> findALl() {
        return ResponseEntity.status(HttpStatus.OK).body(service.list());
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<JwtUserResponse> create(@Valid @RequestBody CreateUserRequest request) {

        service.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authUseCase.authenticate(new LoginRequest(request.getEmail(), request.getPassword())));
    }

    @PutMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.edit(user, id));
    }

    @PatchMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<User> disable(@PathVariable UUID id) {
        service.disableById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
