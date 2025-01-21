package com.eimbee.ecommerce.user.application.dto.response;

import com.eimbee.ecommerce.user.application.dto.UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private final List<UserDTO> users;
}