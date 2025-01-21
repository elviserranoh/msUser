package com.eimbee.ecommerce.user.infrastructure.repository;

import com.eimbee.ecommerce.user.infrastructure.repository.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, UUID> {
    Optional<UserRoleEntity> findByName(String name);
}
