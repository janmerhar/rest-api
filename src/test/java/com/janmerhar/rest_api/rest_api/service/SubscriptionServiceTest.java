package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.janmerhar.rest_api.rest_api.domain.Customer;
import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.domain.Plan;
import com.janmerhar.rest_api.rest_api.domain.Subscription;
import com.janmerhar.rest_api.rest_api.dto.CreateSubscriptionDto;
import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.helper.TestDataFactory;
import com.janmerhar.rest_api.rest_api.repository.CustomerRepository;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.SubscriptionRepository;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void createSubscription() {
        Customer cust = TestDataFactory.customer(1L, "Jan", "a@a.com", CustomerType.INDIVIDUAL);
        Plan plan = TestDataFactory.plan(2L, TestDataFactory.product(5L, "Prod", "Desc"), "Plan", "Desc");
        CreateSubscriptionDto dto = TestDataFactory.createSubscriptionDto(1L, 2L);
        Subscription saved = TestDataFactory.subscription(10L, cust, plan);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(cust));
        when(planRepository.findById(2L)).thenReturn(Optional.of(plan));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(saved);

        SubscriptionResponseDto res = subscriptionService.create(dto);
        assertEquals(10L, res.getId());
        assertEquals(1L, res.getCustomerId());
        assertEquals(2L, res.getPlanId());
    }

    @Test
    void getSubscriptionMissingId() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> subscriptionService.getById(1L));
    }

    @Test
    void getAllSubscriptions() {
        Customer cust = TestDataFactory.customer(1L, "Jan", "a@a.com", CustomerType.INDIVIDUAL);
        Plan plan = TestDataFactory.plan(2L, TestDataFactory.product(5L, "Prod", "Desc"), "Plan", "Desc");
        when(subscriptionRepository.findAll()).thenReturn(List.of(
                TestDataFactory.subscription(1L, cust, plan),
                TestDataFactory.subscription(2L, cust, plan)));

        List<SubscriptionResponseDto> res = subscriptionService.getAll();
        assertEquals(2, res.size());
        assertEquals(2L, res.get(0).getPlanId());
        assertEquals(1L, res.get(1).getCustomerId());
    }
}
