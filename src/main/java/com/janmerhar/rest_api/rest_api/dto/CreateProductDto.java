package com.janmerhar.rest_api.rest_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class CreateProductDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}