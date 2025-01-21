package com.eimbee.ecommerce.user.application.dto.response;

import com.eimbee.ecommerce.user.application.dto.PhoneDTO;
import com.eimbee.ecommerce.user.application.dto.RoleDTO;
import com.eimbee.ecommerce.user.application.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
	
	private String token;
	private String refreshToken;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(UUID id, String name, String email, Boolean isActive, List<RoleDTO> roles, List<PhoneDTO> phones, LocalDate created, LocalDate modified, LocalDate lastLogin, String token, String refreshToken) {
		super(id, name, email, roles, phones, created, modified, lastLogin, isActive);
		this.token = token;
		this.refreshToken = refreshToken;
	}
	
}
