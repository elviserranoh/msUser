package com.eimbee.ecommerce.user.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest implements Serializable {

    @NotBlank(message = "Es requerido")
    private String name;

    @NotBlank(message = "Es requerido")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Debe proporcionar un correo electronico valido")
    private String email;

    // Al menos una letra (minúscula o mayúscula).
    // Al menos un número.
    // Debe tener entre 8 y 20 caracteres
    @NotBlank(message = "Es requerido")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$", message = "Debe proporcionar una contraseña valida")
    private String password;

    @NotEmpty(message = "Es requerido")
    private List<@Valid UserPhoneRequest> phones;
}
