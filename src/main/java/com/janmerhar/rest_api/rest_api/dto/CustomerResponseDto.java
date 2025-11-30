package com.janmerhar.rest_api.rest_api.dto;

import java.time.Instant;
import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import lombok.*;

@Getter
@Setter
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String email;
    private CustomerType type;
    private Instant createdAt;
}
