package com.janmerhar.rest_api.rest_api.service;

import org.springframework.stereotype.Service;

import com.janmerhar.rest_api.rest_api.domain.Customer;
import com.janmerhar.rest_api.rest_api.domain.Plan;
import com.janmerhar.rest_api.rest_api.domain.Subscription;
import com.janmerhar.rest_api.rest_api.dto.CreateSubscriptionDto;
import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.CustomerRepository;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.SubscriptionRepository;

import java.time.Instant;
import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            CustomerRepository customerRepository,
            PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.planRepository = planRepository;
    }

    public SubscriptionResponseDto create(CreateSubscriptionDto dto) {
        Customer c = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Plan p = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        Subscription s = new Subscription();
        s.setCustomer(c);
        s.setPlan(p);
        s.setStartDate(Instant.now());

        Subscription saved = subscriptionRepository.save(s);
        return toResponse(saved);
    }

    public SubscriptionResponseDto getById(Long id) {
        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));

        return toResponse(s);
    }

    public List<SubscriptionResponseDto> getAll() {
        return subscriptionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private SubscriptionResponseDto toResponse(Subscription subscription) {
        SubscriptionResponseDto res = new SubscriptionResponseDto();
        
        res.setId(subscription.getId());
        res.setCustomerId(subscription.getCustomer().getId());
        res.setPlanId(subscription.getPlan().getId());
        res.setStatus(subscription.getStatus());
        res.setStartDate(subscription.getStartDate());
        res.setEndDate(subscription.getEndDate());
        res.setCreatedAt(subscription.getCreatedAt());

        return res;
    }
}