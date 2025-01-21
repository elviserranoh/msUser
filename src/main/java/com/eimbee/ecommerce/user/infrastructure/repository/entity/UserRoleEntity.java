package com.eimbee.ecommerce.user.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_USER_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity implements Serializable {

    @Id
    private UUID id;

    private String name;

    public UserRoleEntity(String name) {
        this.name = name;
    }
}
