package com.eimbee.ecommerce.user.infrastructure.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_PHONES")
public class UserPhoneEntity implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Es requerido")
    private String number;

    @NotBlank(message = "Es requerido")
    private String cityCode;

    @NotBlank(message = "Es requerido")
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
