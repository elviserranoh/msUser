package com.eimbee.ecommerce.user.infrastructure.controller;

import com.eimbee.ecommerce.user.application.dto.PhoneDTO;
import com.eimbee.ecommerce.user.application.dto.RoleDTO;
import com.eimbee.ecommerce.user.application.dto.request.CreateUserRequest;
import com.eimbee.ecommerce.user.application.dto.request.LoginRequest;
import com.eimbee.ecommerce.user.application.dto.request.UserPhoneRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.AuthenticationUserCase;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(UserControllerTest.TestConfig.class)
class UserControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public UserUseCase userUseCase() {
            return mock(UserUseCase.class);
        }

        @Bean
        @Primary
        public AuthenticationUserCase authenticationUserCase() {
            return mock(AuthenticationUserCase.class);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable());
            return http.build();
        }
    }

    private static final String BASE_URL = "/user";

    @Autowired
    private UserUseCase service;

    @Autowired
    private AuthenticationUserCase authUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUserThenReturnHttpStatusIsNotFound() throws Exception {

        LoginRequest loginRequest = new LoginRequest("prueba1@gmail.com", "Ab156564");

        CreateUserRequest request = new CreateUserRequest();
        request.setName("Prueba User");
        request.setEmail("prueba1@gmail.com");
        request.setPassword("Ab156564");
        request.setPhones(
                List.of(UserPhoneRequest.builder()
                        .number("414856654")
                        .cityCode("654")
                        .countryCode("58")
                        .build())
        );

        User user = User.builder()
                .id(UUID.fromString("c8bde052-ce1c-4092-950e-ab55d75721a2"))
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        JwtUserResponse response = JwtUserResponse.jwtUserResponseBuilder()
                .id(UUID.fromString("c8bde052-ce1c-4092-950e-ab55d75721a2"))
                .name("Prueba User")
                .email("prueba1@gmail.com")
                .isActive(true)
                .roles(List.of(new RoleDTO("USER")))
                .phones(List.of(new PhoneDTO(UUID.fromString("59c5c9eb-ef63-4fa4-9a58-aa7953abb396"), "4121858122", "654", "58")))
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .lastLogin(LocalDate.now())
                .token("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IltST0xFX1VTRVJdIiwidG9rZW5UeXBlIjoiSldUIiwiZW1haWwiOiJlc3NoNzc3QGdtYWlsLmNvbSIsImV4cCI6MTczNzQ3MDE5MiwiaWF0IjoxNzM3NDY2NTkyLCJzdWIiOiJjOGJkZTA1Mi1jZTFjLTQwOTItOTUwZS1hYjU1ZDc1NzIxYTIifQ.ZdPZR9HhnkxA_YqSth6rG-d25T5oYcGpW1Du3tzd5bOdYGtRxMCziFblF7OG-ie40oMTwghkjVT5x86_9pIFTQ")
                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IltST0xFX1VTRVJdIiwidG9rZW5UeXBlIjoicmVmcmVzaCIsImVtYWlsIjoiZXNzaDc3N0BnbWFpbC5jb20iLCJleHAiOjE3Mzc0ODgxOTIsImlhdCI6MTczNzQ2NjU5Miwic3ViIjoiYzhiZGUwNTItY2UxYy00MDkyLTk1MGUtYWI1NWQ3NTcyMWEyIn0.biEq9Pfxxp7dG-i_f5SUnLaLaGzLnjsjU5kdmQ4qXH7hW-HbhzoDD8GdN3UzYzHRC50PVg8kCwPVAYR6DemS5w")
                .build();


        when(service.save(request)).thenReturn(user);
        when(authUseCase.authenticate(loginRequest)).thenReturn(response);

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    private static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}