package com.janmerhar.rest_api.rest_api.dto;

import com.janmerhar.rest_api.rest_api.domain.CustomerType;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class CreateCustomerDto {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private CustomerType type;
}