package com.eimbee.ecommerce.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private UUID id;
    private String number;
    private String cityCode;
    private String countryCode;
}
