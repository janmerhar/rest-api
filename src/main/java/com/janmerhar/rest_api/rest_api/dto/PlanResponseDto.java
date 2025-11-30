package com.janmerhar.rest_api.rest_api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
public class PlanResponseDto {
    private Long id;
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Instant createdAt;
}
