package com.janmerhar.rest_api.rest_api.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.janmerhar.rest_api.rest_api.domain.CustomerType;
import com.janmerhar.rest_api.rest_api.dto.CreateCustomerDto;
import com.janmerhar.rest_api.rest_api.dto.CreatePlanDto;
import com.janmerhar.rest_api.rest_api.dto.CreateProductDto;
import com.janmerhar.rest_api.rest_api.dto.CreateSubscriptionDto;
import com.janmerhar.rest_api.rest_api.dto.ProductResponseDto;
import com.janmerhar.rest_api.rest_api.dto.SubscriptionResponseDto;
import com.janmerhar.rest_api.rest_api.exception.NotFoundException;
import com.janmerhar.rest_api.rest_api.repository.CustomerRepository;
import com.janmerhar.rest_api.rest_api.repository.PlanRepository;
import com.janmerhar.rest_api.rest_api.repository.ProductRepository;
import com.janmerhar.rest_api.rest_api.repository.SubscriptionRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@ActiveProfiles("test")
class SubscriptionServiceIntegrationTest {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PlanService planService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlanRepository planRepository;

    private Long customerId;
    private Long planId;

    @BeforeEach
    void setUp() {
        subscriptionRepository.deleteAll();
        planRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();

        CreateCustomerDto customerDto = new CreateCustomerDto();
        customerDto.setName("Jan");
        customerDto.setEmail("a@a.com");
        customerDto.setType(CustomerType.INDIVIDUAL);
        customerId = customerService.create(customerDto).getId();

        CreateProductDto productDto = new CreateProductDto();
        productDto.setName("prod");
        productDto.setDescription("desc");
        ProductResponseDto product = productService.create(productDto);

        CreatePlanDto planDto = new CreatePlanDto();
        planDto.setProductId(product.getId());
        planDto.setName("plan");
        planDto.setDescription("desc");
        planDto.setPrice(java.math.BigDecimal.TEN);
        planId = planService.create(planDto).getId();
    }

    @Test
    void createSubscription() {
        CreateSubscriptionDto dto = new CreateSubscriptionDto();
        dto.setCustomerId(customerId);
        dto.setPlanId(planId);

        SubscriptionResponseDto created = subscriptionService.create(dto);
        assertNotNull(created.getId());

        SubscriptionResponseDto fetched = subscriptionService.getById(created.getId());
        assertEquals(customerId, fetched.getCustomerId());
        assertEquals(planId, fetched.getPlanId());
        assertNotNull(fetched.getStartDate());
    }

    @Test
    void createSubscriptionWithoutPlan() {
        CreateSubscriptionDto dto = new CreateSubscriptionDto();
        dto.setCustomerId(customerId);
        dto.setPlanId(111L);

        assertThrows(NotFoundException.class, () -> subscriptionService.create(dto));
    }
}
