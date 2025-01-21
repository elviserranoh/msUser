package com.eimbee.ecommerce.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<UserPhone> phones;
    private List<UserRole> roles;
    private Boolean isActive;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate lastLogin;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phones=" + phones +
                ", roles=" + roles +
                ", isActive=" + isActive +
                ", created=" + created +
                ", modified=" + modified +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
