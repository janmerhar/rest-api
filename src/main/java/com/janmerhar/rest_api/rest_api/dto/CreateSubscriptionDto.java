package com.janmerhar.rest_api.rest_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class CreateSubscriptionDto {
    @NotNull
    private Long customerId;

    @NotNull
    private Long planId;
}