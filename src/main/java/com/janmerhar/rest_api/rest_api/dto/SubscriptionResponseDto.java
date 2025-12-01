package com.janmerhar.rest_api.rest_api.dto;

import java.time.Instant;

import com.janmerhar.rest_api.rest_api.domain.SubscriptionStatus;

import lombok.*;

@Getter
@Setter
public class SubscriptionResponseDto {
    private Long id;
    private Long customerId;
    private Long planId;
    private SubscriptionStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant createdAt;
}