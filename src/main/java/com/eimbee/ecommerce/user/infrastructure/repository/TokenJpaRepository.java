package com.eimbee.ecommerce.user.infrastructure.repository;

import com.eimbee.ecommerce.user.infrastructure.repository.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);

    List<TokenEntity> findAllValidTokensByUserId(UUID userId);

    @Query("SELECT t FROM TokenEntity t WHERE t.user.id = :userId AND t.revoked = false")
    List<TokenEntity> findAllValidTokensByUser(UUID userId);
}
