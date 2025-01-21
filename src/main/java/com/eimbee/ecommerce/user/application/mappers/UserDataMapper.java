package com.eimbee.ecommerce.user.application.mappers;

import com.eimbee.ecommerce.user.application.dto.PhoneDTO;
import com.eimbee.ecommerce.user.application.dto.RoleDTO;
import com.eimbee.ecommerce.user.application.dto.request.CreateUserRequest;
import com.eimbee.ecommerce.user.application.dto.response.JwtUserResponse;
import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.model.UserPhone;
import com.eimbee.ecommerce.user.domain.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDataMapper {

    public User UserRequestToUserDomain(CreateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(List.of(new UserRole("USER")))
                .phones(request.getPhones().stream().map(
                        phone -> UserPhone.builder()
                                .number(phone.getNumber())
                                .cityCode(phone.getCityCode())
                                .countryCode(phone.getCountryCode())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }


    public JwtUserResponse userDomainAndTokenToJwtUserResponse(User user, String jwtToken, String jwtRefreshToken) {

        return JwtUserResponse.jwtUserResponseBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(rol -> new RoleDTO(rol.getName())).collect(Collectors.toList()))
                .phones(user.getPhones().stream()
                        .map(phone -> new PhoneDTO(phone.getId(), phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                        .collect(Collectors.toList()))
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

}
