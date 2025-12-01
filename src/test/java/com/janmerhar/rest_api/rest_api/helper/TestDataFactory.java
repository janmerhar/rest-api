package com.janmerhar.rest_api.rest_api.helper;

import java.time.Instant;

import com.janmerhar.rest_api.rest_api.domain.Customer;
import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.domain.Plan;
import com.janmerhar.rest_api.rest_api.domain.Product;
import com.janmerhar.rest_api.rest_api.domain.Subscription;
import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.CreateSubscriptionDto;
import com.janmerhar.rest_api.rest_api.dto.CustomerResponseDto;
import com.janmerhar.rest_api.rest_api.dto.PlanResponseDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;

public final class TestDataFactory {
    public static CreateCustomerDto createCustomerDto(String name, String email, CustomerType type) {
        CreateCustomerDto dto = new CreateCustomerDto();
        
        dto.setName(name);
        dto.setEmail(email);
        dto.setType(type);

        return dto;
    }

    public static CreateProductDto createProductDto(String name, String description) {
        CreateProductDto dto = new CreateProductDto();

        dto.setName(name);
        dto.setDescription(description);

        return dto;
    }

    public static CreatePlanDto createPlanDto(Long productId, String name, String description) {
        CreatePlanDto dto = new CreatePlanDto();

        dto.setProductId(productId);
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(java.math.BigDecimal.TEN);

        return dto;
    }

    public static CreateSubscriptionDto createSubscriptionDto(Long customerId, Long planId) {
        CreateSubscriptionDto dto = new CreateSubscriptionDto();

        dto.setCustomerId(customerId);
        dto.setPlanId(planId);

        return dto;
    }

    public static Customer customer(Long id, String name, String email, CustomerType type) {
        Customer c = new Customer();

        c.setId(id);
        c.setName(name);
        c.setEmail(email);
        c.setType(type);
        c.setCreatedAt(Instant.now());

        return c;
    }

    public static Product product(Long id, String name, String description) {
        Product p = new Product();

        p.setId(id);
        p.setName(name);
        p.setDescription(description);
        p.setCreatedAt(Instant.now());

        return p;
    }

    public static Plan plan(Long id, Product product, String name, String description) {
        Plan p = new Plan();

        p.setId(id);
        p.setProduct(product);
        p.setName(name);
        p.setDescription(description);
        p.setPrice(java.math.BigDecimal.TEN);
        p.setCreatedAt(Instant.now());
        
        return p;
    }

    public static Subscription subscription(Long id, Customer customer, Plan plan) {
        Subscription s = new Subscription();

        s.setId(id);
        s.setCustomer(customer);
        s.setPlan(plan);
        s.setStartDate(Instant.now());
        s.setCreatedAt(Instant.now());
        
        return s;
    }

    public static CustomerResponseDto customerResponseDto(Long id, String name, String email, CustomerType type) {
        CustomerResponseDto dto = new CustomerResponseDto();

        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setType(type);
        dto.setCreatedAt(Instant.now());

        return dto;
    }

    public static ProductResponseDto productResponseDto(Long id, String name, String description) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setCreatedAt(Instant.now());

        return dto;
    }

    public static PlanResponseDto planResponseDto(Long id, Long productId, String name, String description) {
        PlanResponseDto dto = new PlanResponseDto();

        dto.setId(id);
        dto.setProductId(productId);
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(java.math.BigDecimal.TEN);
        dto.setCreatedAt(Instant.now());
        
        return dto;
    }

    public static SubscriptionResponseDto subscriptionResponseDto(Long id, Long customerId, Long planId) {
        SubscriptionResponseDto dto = new SubscriptionResponseDto();
        
        dto.setId(id);
        dto.setCustomerId(customerId);
        dto.setPlanId(planId);
        dto.setStatus(com.janmerhar.rest_api.rest_api.domain.SubscriptionStatus.ACTIVE);
        dto.setStartDate(Instant.now());
        dto.setCreatedAt(Instant.now());

        return dto;
    }
}
