package com.janmerhar.rest_api.rest_api.dto;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
}