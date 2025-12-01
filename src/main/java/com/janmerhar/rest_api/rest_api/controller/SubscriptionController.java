package com.janmerhar.rest_api.rest_api.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.janmerhar.rest_api.rest_api.dto.CreateSubscriptionDto;
import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;
import com.janmerhar.rest_api.rest_api.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponseDto create(@Valid @RequestBody CreateSubscriptionDto dto) {
        return subscriptionService.create(dto);
    }

    @GetMapping
    public List<SubscriptionResponseDto> getAll() {
        return subscriptionService.getAll();
    }

    @GetMapping("/{id}")
    public SubscriptionResponseDto getOne(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }
}