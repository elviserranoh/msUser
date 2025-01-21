package com.eimbee.ecommerce.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPhoneRequest implements Serializable {

    @NotBlank(message = "Es requerido")
    private String number;

    @NotBlank(message = "Es requerido")
    private String cityCode;

    @NotBlank(message = "Es requerido")
    private String countryCode;

}
