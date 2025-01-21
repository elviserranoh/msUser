package com.eimbee.ecommerce.user.infrastructure.mapper;

import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.model.UserPhone;
import com.eimbee.ecommerce.user.domain.model.UserRole;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserEntity;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserPhoneEntity;
import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class UserMapper {

    public User UserEntityToUserDomain(UserEntity userEntity) {

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .isActive(userEntity.getIsActive())
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .roles(userEntity.getRoles().stream().map(rol -> new UserRole(rol.getName())).collect(Collectors.toList()))
                .phones(userEntity.getPhones().stream().map(
                        phone -> UserPhone.builder()
                                .id(phone.getId())
                                .number(phone.getNumber())
                                .cityCode(phone.getCityCode())
                                .countryCode(phone.getCountryCode())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    public UserEntity UserDomainToUserEntity(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(rol -> new UserRoleEntity(rol.getName())).collect(Collectors.toList()))
                .phones(user.getPhones().stream().map(
                        phone -> UserPhoneEntity.builder()
                                .id(phone.getId())
                                .number(phone.getNumber())
                                .cityCode(phone.getCityCode())
                                .countryCode(phone.getCountryCode())
                                .build()).collect(Collectors.toList()))
                .isActive(true)
                .build();

        userEntity.getPhones().forEach(phone -> phone.setUser(userEntity));

        return userEntity;

    }

    public User userDetailsToUserDomain(UserDetails userDetails) {
        return User.builder()
                .email(userDetails.getUsername())
                .password(userDetails.getPassword())
                .roles(userDetails.getAuthorities().stream()
                        .map(rol ->
                                new UserRole(rol.getAuthority()))
                        .collect(Collectors.toList()))
                .build();
    }

}
