package com.eimbee.ecommerce.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	private UUID id;
	private String name;
	private String email;
	private List<RoleDTO> roles;
	private List<PhoneDTO> phones;
	private LocalDate created;
	private LocalDate modified;
	private LocalDate lastLogin;
	private Boolean isActive;

}
