package com.eimbee.ecommerce.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    private UUID id;
    private String token;
    private Boolean revoked;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private User user;
}
